package de.filios.interpreters.jlox;

public class LoxClass {

    private String name;

    public LoxClass(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
