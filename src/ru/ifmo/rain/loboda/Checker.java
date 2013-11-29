package ru.ifmo.rain.loboda;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Checker {
    private static List<Expression> axioms;
    private static boolean isInit;
    private Set<Expression> hypothesis;

    public Checker(List<Expression> hypothesis) throws IOException {
        if (!isInit) {
            init();
        }
        this.hypothesis = new HashSet<Expression>(hypothesis);
    }

    private void init() throws IOException {
        isInit = true;
        InputStream stream = Checker.class.getResourceAsStream("Resources/ClassicalAxioms");
        axioms = Parser.toParse(new LogicStreamTokenizer(stream));
    }

    public Type check(Expression expression) {
        Type result = Type.ERROR;
        if (hypothesis.contains(expression)) {
            result = Type.HYPOTHESIS;
        }
        for (Expression axiom : axioms) {
            if (expression.isIsomorphic(axiom)) {
                result = Type.CLASSICAL_AXIOM;
            }
        }

        return result;
    }

    public enum Type {
        HYPOTHESIS, ERROR, CLASSICAL_AXIOM, MODUS_PONENS_1, MODUS_PONENS_2, MODUS_PONENS_3
    }
}
