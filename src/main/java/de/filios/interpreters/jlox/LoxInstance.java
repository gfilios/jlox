package de.filios.interpreters.jlox;

import java.util.List;

public class LoxInstance{


    private LoxClass klass;

    public LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }
}
