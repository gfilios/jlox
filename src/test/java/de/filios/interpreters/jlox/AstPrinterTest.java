package de.filios.interpreters.jlox;

import de.filios.interpreters.jlox.AstPrinter;
import de.filios.interpreters.jlox.Expr;
import de.filios.interpreters.jlox.Token;
import de.filios.interpreters.jlox.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstPrinterTest {

    @Test
    void print() {
        Expr expression = new Expr.Binary(
                new Expr.Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Expr.Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Expr.Grouping(new Expr.Literal("45.67")));

        String result = new AstPrinter().print(expression);
        assertEquals("(* (- 123) (group 45.67))", result);
    }
}