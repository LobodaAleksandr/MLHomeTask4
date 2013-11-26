package ru.ifmo.rain.loboda;

public class Existance extends Expression{
    Expression expression;
    Variable variable;
    public Existance(Variable variable, Expression expression){
        this.expression = expression;
        this.variable = variable;
    }
    @Override
    public String toString() {
        return "?" + variable.toString() + "(" + expression.toString() + ")";
    }
}
