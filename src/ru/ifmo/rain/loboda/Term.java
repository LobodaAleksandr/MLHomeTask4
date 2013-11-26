package ru.ifmo.rain.loboda;

public class Term {
    private Term[] terms;
    protected String name;
    public Term(String name, Term[] terms){
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
