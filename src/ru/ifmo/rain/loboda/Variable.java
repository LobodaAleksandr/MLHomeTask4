package ru.ifmo.rain.loboda;

import java.util.Set;

public class Variable extends Term {
    public static String impossibleVariable = "It's impossible variable";

    public Variable(String name) {
        super(name, null);
    }

    @Override
    public boolean existsFree(Variable var) {
        return var.equals(this);
    }

    public boolean equals(Object object) {
        if(name.equals(impossibleVariable) && object instanceof Term){
            if(Expression.equalsWorkAround == null){
                Expression.equalsWorkAround = (Term)object;
            } else {
                return Expression.equalsWorkAround.equals(object);
            }
        }
        return object.getClass() == Variable.class && ((Term) object).getName().equals(name);
    }

    protected boolean freeToSubstitute(Variable from, Variable[] to, Set<Variable> blocked){
        if(equals(from)){
            for(Variable var: to){
                if(blocked.contains(var)){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void getVariables(Set<Variable> variables){
        variables.add(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
