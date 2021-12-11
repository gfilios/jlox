package de.filios.interpreters.jlox;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest extends TestStandardOutErr{

    @Test
    void interpretSimpleAndCorrectExpression() {
        Expr expression = scanAndParse("-123 * (45.67+37)");
        assertEquals("-10168.41", new Interpreter().interpret(expression));
    }

    @Test
    void interpretBooleanNegation() {
        Expr expression = scanAndParse("!(3==3)");
        assertEquals("false", new Interpreter().interpret(expression));
    }

    @Test
    void interpretCompare() {
        Expr expression = scanAndParse("!(4>5)");
        assertEquals("true", new Interpreter().interpret(expression));
    }

    @Test
    void interpretWrongDoubles() {

        this.setUpErrorStreamCaption();
        Expr expression = scanAndParse("\"georg\"*6");
        new Interpreter().interpret(expression);
        assertEquals("*  Operands must be numbers\n[line 1]\n", errorStreamCaptor.toString());
        this.tearDownErrorStreamCaption();
    }

    @Test
    void addStringsAndDouble() {
        Expr expression = scanAndParse("\"georg\"+6");
        assertEquals("georg6", new Interpreter().interpret(expression));
        expression = scanAndParse("12+\"georg\"");
        assertEquals("12georg", new Interpreter().interpret(expression));
    }

    @Test
    void interpretStringConcate() {
        Expr expression = scanAndParse("\"georg\" + \"filios\"");
        assertEquals("georgfilios", new Interpreter().interpret(expression));
    }

    @Test
    void divideByZero() {
        this.setUpErrorStreamCaption();
        Expr expression = scanAndParse("18/0");
        new Interpreter().interpret(expression);
        assertEquals("/  Divide by Zero\n[line 1]\n", errorStreamCaptor.toString());
        this.tearDownErrorStreamCaption();
    }


    private Expr scanAndParse(String string) {
        Scanner scanner = new Scanner(string);
        List<Token> scannedTokens = scanner.scanTokens();
        Parser parser = new Parser(scannedTokens);
        Expr expression = parser.parse();
        return expression;
    }
}