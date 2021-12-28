package de.filios.interpreters.jlox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoxInstance{

    private LoxClass klass;

    private final Map<String, Object> fields = new HashMap<>();

    public LoxInstance(LoxClass klass) {
        this.klass = klass;
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }

    public Object get(Token name) {
        if (fields.containsKey(name.lexeme)){
            return fields.get(name.lexeme);
        }

        LoxFunction method = klass.findMethod(name.lexeme);
        if (method!=null) return method;

        throw new RuntimeError(name,"Undefined property '" + name.lexeme + "'.");
    }

    public void set(Token name, Object field) {
        fields.put(name.lexeme,field);
    }


}