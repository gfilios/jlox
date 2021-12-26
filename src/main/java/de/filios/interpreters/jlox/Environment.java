package de.filios.interpreters.jlox;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Object> values = new HashMap<>();
    private final Environment enclosing;

    public Environment() {
        this.enclosing = null;
    }

    public Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }


    void define(String name, Object value){
        values.put(name,value);
    }

    void define(Token name, Object value){
        this.define(name.lexeme,value);
    }

    void assign (Token name, Object value){
        if (values.containsKey(name.lexeme)){
            values.put(name.lexeme,value);
            return;
        } else if (this.enclosing!=null) {
            this.enclosing.assign(name,value);
            return;
        }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme +"'.");
    }

    Object get (Token name) {
        if (values.containsKey(name.lexeme)){
            return values.get(name.lexeme);
        } else if (this.enclosing!=null) {
            return this.enclosing.get(name);
        }
        throw new RuntimeError(name, "Undefined variable '" + name.lexeme +"'.");
    }



}
