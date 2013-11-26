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
        if (left.getClass() == Variable.class) {
            leftS = left.toString();
        } else {
            leftS = "(" + left.toString() + ")";
        }
        if (right.getClass() == Variable.class) {
            rightS = right.toString();
        } else {
            rightS = "(" + right.toString() + ")";
        }
        stringCache = leftS + getSign() + rightS;
        return stringCache;
    }

    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation) {
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            return left.equals(((BinaryOperation) obj).getLeft()) && right.equals(((BinaryOperation) obj).getRight());
        } else {
            return false;
        }
    }

    protected abstract String getSign();

}
