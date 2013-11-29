package ru.ifmo.rain.loboda;

import java.util.Set;

public class Term {
    private Term[] terms;
    protected String name;

    public Term(String name, Term[] terms) {
        this.terms = terms;
        this.name = name;
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

    public boolean existsFree(Variable var) {
        for (Term term : terms) {
            if (term.existsFree(var)) {
                return true;
            }
        }
        return false;
    }

    protected boolean freeToSubstitute(Variable from, Variable to, boolean blocked){
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

    protected void getVariables(Set<Variable> variables){
        for(Term term: terms){
            term.getVariables(variables);
        }
    }

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

    public int hashCode(){
        return toString().hashCode();
    }

    public boolean equals(Object object) {
        if (object.getClass() != Term.class) {
            return false;
        }
        Term term = (Term) object;
        if (!term.getName().equals(name)) {
            return false;
        }
        if (terms == null && term.getTerms() == null) {
            return true;
        }
        if (term.getTerms().length != terms.length) {
            return false;
        }
        Term[] termsToComp = term.getTerms();
        for (int i = 0; i < terms.length; i++) {
            if (!terms[i].equals(termsToComp[i])) {
                return false;
            }
        }
        return true;
    }
}
