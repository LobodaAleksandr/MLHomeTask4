package ru.ifmo.rain.loboda;

public class Variable extends Term{
    public Variable(String name){
        super(name, null);
    }

    @Override
    public String toString() {
        return name;
    }
}
