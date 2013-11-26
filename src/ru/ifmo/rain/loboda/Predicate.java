package ru.ifmo.rain.loboda;

public class Predicate extends Expression {
    private Term[] terms;
    private String name;

    public Predicate(String name, Term[] terms){
        this.terms = terms;
        this.name = name;
    }

    @Override
    public String toString() {
        String rep = name;
        if(terms != null){
            rep += "(";
            for(int i = 0; i < terms.length; i++){
                rep += terms[i].toString();
                if(i != terms.length - 1){
                    rep += ", ";
                }
            }
            rep += ")";
        }
        return rep;
    }
}
