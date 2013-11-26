package ru.ifmo.rain.loboda;

import java.util.ArrayList;

public class OperationOr extends BinaryOperation {
    public OperationOr(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getSign() {
        return "|";
    }
}
