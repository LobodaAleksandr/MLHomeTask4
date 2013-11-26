package ru.ifmo.rain.loboda;

public abstract class BinaryOperation extends Expression {
    private Expression left, right;
    private String stringCache;

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public String toString() {
        if (stringCache != null) {
            return stringCache;
        }
        String leftS;
        String rightS;
        leftS = "(" + left.toString() + ")";
        rightS = "(" + right.toString() + ")";
        stringCache = leftS + getSign() + rightS;
        return stringCache;
    }

    protected abstract String getSign();

}
