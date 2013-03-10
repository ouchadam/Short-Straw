package com.ouchadam.shortstraw;

public class Straw {

    private final boolean isShort;

    public Straw(boolean isShort) {
        this.isShort = isShort;
    }

    public String isShort() {
        return isShort ? "short" : "not short";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Straw straw = (Straw) o;

        if (isShort != straw.isShort) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (isShort ? 1 : 0);
    }
}