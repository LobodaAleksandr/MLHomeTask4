package ru.ifmo.rain.loboda;

public abstract class Expression {
    private int hashCache;
    private boolean isHashComputed;

    public abstract String toString();

    @Override
    public int hashCode() {
        if (isHashComputed) {
            return hashCache;
        }
        hashCache = toString().hashCode();
        isHashComputed = true;
        return hashCache;
    }
}

