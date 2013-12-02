package ru.ifmo.rain.loboda;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static String why;
    private static Expression parcel;

    private static List<Expression> substitude(String Resource, Expression A, Expression B, Expression C, Variable x) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("Resources/" + Resource)));
        StringWriter stringWriter = new StringWriter();
        for(String line = reader.readLine(); line != null; line = reader.readLine()){
            if(A != null)
                line = line.replace("A", "(" + A.toString() + ")");
            if(B != null)
                line = line.replace("B", "(" + B.toString() + ")");
            if(C != null)
                line = line.replace("C", "(" + C.toString() + ")");
            if(x != null)
                line = line.replace("x", x.toString());
            stringWriter.write(line + "\n");
        }
        String result = stringWriter.toString();
        Parser parser = new Parser(new ByteArrayInputStream(result.getBytes("UTF-8")));
        List<Expression> expressions = new ArrayList<Expression>();
        while(parser.hasNext()){
            expressions.add(parser.next());
        }
        return expressions;
    }

    public static List<Expression> deduction(Parser parser) throws IOException {
        List<Expression> hypothesis = parser.getHypothesis();
        parcel = hypothesis.get(hypothesis.size() - 1);
        hypothesis = hypothesis.subList(0, hypothesis.size() - 1);
        Checker checker = new Checker(hypothesis);
        List<Expression> result = new LinkedList<Expression>();
        Expression toProve = parser.next();
        Expression e = null;
        while(parser.hasNext()){
            e = parser.next();
            Checker.Type type = checker.check(e);
            switch (type){
                case HYPOTHESIS:
                case CLASSICAL_AXIOM:
                case PREDICATE_AXIOM:
                    if(e.equals(parcel)){
                        result.addAll(substitude("AfollowA", e, null, null, null));
                    } else {
                        result.addAll(substitude("ifAxiom", e, parcel, null, null));
                    }
                    break;
                case MODUS_PONENS_1:
                    result.addAll(substitude("MP1", parcel, checker.getLastPonens(), e, null));
                    break;
                case MODUS_PONENS_2:
                    Expression A = parcel;
                    Expression B = ((Implication)e).getLeft();
                    Expression C = ((Universal)((Implication)e).getRight()).getExpression();
                    Variable x = ((Universal)((Implication)e).getRight()).getVariable();
                    result.addAll(substitude("MP2", A, B, C, x));
                    break;
                case MODUS_PONENS_3:
                    A = parcel;
                    B = ((Universal)((Implication)e).getLeft()).getExpression();
                    C = ((Implication)e).getRight();
                    x = ((Universal)((Implication)e).getLeft()).getVariable();
                    result.addAll(substitude("MP3", A, B, C, x));
                    break;
                case ERROR:
                    why = checker.getLastError();
                    return null;
            }
        }
        if(e == null || !toProve.equals(e)){
            why = "Последнее выражение должно совпадать с доказываемым";
            return null;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            System.err.println("Программа принимает ровно 2 аргумента");
            System.exit(1);
        }
        Parser parser = new Parser(new FileInputStream(args[0]));
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(args[1])));
        List<Expression> proof = deduction(parser);
        if(proof != null){
            for(Expression e: proof){
                printWriter.println(e);
            }
        } else {
            printWriter.print(why);
            if(why.charAt(why.length() - 1) == ' '){
                printWriter.print(parcel);
            }
        }
        printWriter.close();
    }
}
