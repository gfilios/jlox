package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterExpressionTest extends TestStandardOutErr {


    @BeforeEach
    void initCaptors() {

        this.setUpOuputStreamCaption();
        this.setUpErrorStreamCaption();
    }

    @AfterEach
    void tearDownCaptors() {
        this.tearDownErrorStreamCaption();
        this.tearDownOuputStreamCaption();
    }


    @Test
    void printDouble() {
        List<Stmt> statements = scanAndParse("print 3;");
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("3\n", outputStreamCaptor.toString());
    }

    @Test
    void printCorrectExpression() {
        List<Stmt> statements = scanAndParse("print -123 * (45.67+37);");
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("-10168.41\n", getOutputStreamAndTearDown());
    }

    @Test
    void printBooleanNegation() {
        List<Stmt> statements = scanAndParse("print !(3==3);");
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("false\n", getOutputStreamAndTearDown());
    }

    @Test
    void printComparison() {
        List<Stmt> statements = scanAndParse("print !(4>5);");
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("true\n", getOutputStreamAndTearDown());
    }

    @Test
    void wrongExpressionMixingDoubles() {

        List<Stmt> statements = scanAndParse("print \"georg\"*6;");
        new Interpreter().interpret(statements);
        assertEquals("*  Operands must be numbers\n[line 1]\n", getErrorStreamAndTearDown());
    }

    @Test
    void printAddStringsWithDouble() {
        List<Stmt> statements = scanAndParse("print \"georg\"+6;");
        new Interpreter().interpret(statements);
        assertEquals("georg6\n", getOutputStream());

        statements = scanAndParse("print 12+\"georg\";");
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("georg6\n12georg\n", getOutputStreamAndTearDown());
    }

    @Test
    void interpretStringConcate() {
        List<Stmt> statements = scanAndParse("print \"georg\" + \"filios\";");
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("georgfilios\n", getOutputStreamAndTearDown());
    }

    @Test
    void divideByZero() {
        List<Stmt> statements = scanAndParse("print 18/0;");
        new Interpreter().interpret(statements);
        assertEquals("/  Divide by Zero\n[line 1]\n", getErrorStreamAndTearDown());
    }

    @Test
    void testMultipleStatements() {
        List<Stmt> statements = scanAndParse("print 18;print 282*3;");
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("18\n846\n",  getOutputStreamAndTearDown());
    }



}