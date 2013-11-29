package ru.ifmo.rain.loboda;

import java.util.HashMap;
import java.util.Set;

public class Existance extends Expression {
    Expression expression;
    Variable variable;

    public Existance(Variable variable, Expression expression) {
        this.expression = expression;
        this.variable = variable;
    }

    @Override
    public String toString() {
        return "?" + variable.toString() + "(" + expression.toString() + ")";
    }

    @Override
    protected boolean isomorphic(Expression expression, HashMap<Predicate, Expression> vars) {
        return this.expression.isIsomorphic(((Existance) expression).getExpression(), vars);
    }

    @Override
    public boolean existsFree(Variable var) {
        if (variable.equals(var)) {
            return false;
        } else {
            return expression.existsFree(var);
        }
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object object) {
        return object.getClass() == Existance.class && ((Existance) object).getExpression().equals(expression);
    }

    @Override
    protected boolean freeToSubstitute(Variable from, Variable[] to, Set<Variable> blocked){
        boolean blockedHere = true;
        if(blocked.contains(variable)){
            blockedHere = false;
        } else {
            blocked.add(variable);
        }
        boolean result = expression.freeToSubstitute(from, to, blocked);
        if(blockedHere){
            blocked.remove(variable);
        }
        return result;
    }
}
