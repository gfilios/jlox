package de.filios.interpreters.jlox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoxClass implements LoxCallable {

    String name;
    Map<String, LoxFunction> methods;

    @Override
    public int arity() {
        return 0;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        LoxInstance instance = new LoxInstance(this);
        return instance;
    }

    public LoxClass(String name, Map<String, LoxFunction> methods) {
        this.name = name;
        this.methods = methods;
    }

    @Override
    public String toString() {
        return name;
    }

    public LoxFunction findMethod(String name){
        if (this.methods.containsKey(name)) {
            return this.methods.get(name);
        }
        return null;
    }

}
