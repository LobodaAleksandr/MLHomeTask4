package ru.ifmo.rain.loboda;

import java.io.*;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        InputStream stream = new FileInputStream(new File("in"));
        PrintWriter writer = new PrintWriter("result");
        List<Expression> expressions = Parser.toParse(new LogicStreamTokenizer(stream));
        for(Expression e: expressions){
            writer.println(e);
        }
        writer.close();
    }
}
