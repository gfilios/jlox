package de.filios.interpreters.jlox;

public class RuntimeError extends RuntimeException{


    private final Token token;

    RuntimeError(Token token, String message) {
        super(message);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String getMessage() {
        return token.lexeme + " " + super.getMessage();
    }
}
