package ru.ifmo.rain.loboda;

public class Variable extends Expression {
    char ch;

    public Variable(char ch) {
        this.ch = ch;
    }

    char getName() {
        return ch;
    }

    public boolean equals(Object obj) {
        if (obj.getClass() == Variable.class) {
            return ch == ((Variable) obj).getName();
        } else {
            return false;
        }
    }

    public String toString() {
        return (new Character(ch)).toString();
    }
}
