package ru.ifmo.rain.loboda;

import java.util.HashMap;
import java.util.Set;

public class Predicate extends Expression {
    private Term[] terms;
    private String name;

    public Predicate(String name, Term[] terms) {
        this.terms = terms;
        this.name = name;
    }

    @Override
    protected boolean isomorphic(Expression expression, HashMap<Predicate, Expression> vars) {
        return false;
    }

    public String getName() {
        return name;
    }

    public Term[] getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        String rep = name;
        if (terms != null) {
            rep += "(";
            for (int i = 0; i < terms.length; i++) {
                rep += terms[i].toString();
                if (i != terms.length - 1) {
                    rep += ", ";
                }
            }
            rep += ")";
        }
        return rep;
    }

    @Override
    public boolean existsFree(Variable var) {
        if (terms == null) {
            return false;
        }
        for (Term term : terms) {
            if (term.existsFree(var)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean freeToSubstitute(Variable from, Variable[] to, Set<Variable> blocked) {
        if(terms == null){
            return true;
        }
        for(Term term: terms){
            if(!term.freeToSubstitute(from, to, blocked)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() != Predicate.class) {
            return false;
        }
        Predicate predicate = (Predicate) object;
        if (!predicate.getName().equals(name)) {
            return false;
        }
        if (terms == null && predicate.getTerms() == null) {
            return true;
        }
        if (predicate.getTerms().length != terms.length) {
            return false;
        }
        Term[] termsToComp = predicate.getTerms();
        for (int i = 0; i < terms.length; i++) {
            if (!terms[i].equals(termsToComp[i])) {
                return false;
            }
        }
        return true;
    }

}
