package ru.ifmo.rain.loboda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private static Token token;
    private static LogicStreamTokenizer tokenizer;

    private static void skip() {
        token = null;
    }

    public static List<Expression> toParse(LogicStreamTokenizer logicTokenizer) throws IOException {
        List<Expression> list = new ArrayList<Expression>();
        tokenizer = logicTokenizer;
        token = Token.PRINT;
        while (true) {
            token = tokenizer.nextToken();
            if (token == Token.PRINT) {
                continue;
            }
            if (token == Token.END) {
                break;
            }
            list.add(expression());
        }
        return list;
    }


    private static Term term() throws IOException {
        if (token == null) {
            token = tokenizer.nextToken();
        }
        String name = tokenizer.get_name();
        token = tokenizer.nextToken();
        if (token == Token.LP) {
            List<Term> terms = new LinkedList<Term>();
            token = Token.COMMA;
            while (token == Token.COMMA) {
                skip();
                terms.add(term());
            }
            token = tokenizer.nextToken();
            return new Term(name, terms.toArray(new Term[0]));
        } else {
            return new Variable(name);
        }
    }

    private static Expression predicate() throws IOException {
        if (token == null) {
            token = tokenizer.nextToken();
        }
        String name = tokenizer.get_name();
        token = tokenizer.nextToken();
        if (token == Token.LP) {
            List<Term> terms = new LinkedList<Term>();
            token = Token.COMMA;
            while (token == Token.COMMA) {
                skip();
                terms.add(term());
            }
            token = tokenizer.nextToken();
            return new Predicate(name, terms.toArray(new Term[0]));
        } else {
            return new Predicate(name, null);
        }
    }


    private static Expression unary() throws IOException {
        if (token == null) {
            token = tokenizer.nextToken();
        }
        switch (token) {
            case NOT:
                skip();
                return new OperationNot(unary());
            case UNIVERSAL:
                skip();
                tokenizer.nextToken();
                Variable var = new Variable(tokenizer.get_name());
                skip();
                return new Universal(var, unary());
            case PREDICATE:
                return predicate();
            case EXISTENCE:
                skip();
                tokenizer.nextToken();
                var = new Variable(tokenizer.get_name());
                skip();
                return new Existance(var, unary());
            case LP:
                skip();
                Expression expression = expression();
                token = tokenizer.nextToken();
                return expression;
            default:
                return null;
        }
    }

    private static Expression conjunction() throws IOException {
        Expression left = unary();
        while (token == Token.AND) {
            skip();
            left = new OperationAnd(left, unary());
        }
        return left;
    }

    private static Expression disjunction() throws IOException {
        Expression left = conjunction();
        while (token == Token.OR) {
            skip();
            left = new OperationOr(left, conjunction());
        }
        return left;
    }

    private static Expression expression() throws IOException {
        Expression left = disjunction();
        if (token == Token.IMPLICATION) {
            skip();
            return new Implication(left, expression());
        }
        return left;
    }
}
