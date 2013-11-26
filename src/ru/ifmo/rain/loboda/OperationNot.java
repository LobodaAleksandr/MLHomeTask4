package ru.ifmo.rain.loboda;


public class OperationNot extends Expression {
    private Expression expression;

    public OperationNot(Expression expression) {
        this.expression = expression;
    }

    Expression getExpression() {
        return expression;
    }

    public boolean equals(Object obj) {
        if (obj.getClass() == OperationNot.class) {
            return expression.equals(((OperationNot) obj).getExpression());
        } else {
            return false;
        }
    }

    public String toString() {
        return "!(" + expression.toString() + ")";
    }
}
