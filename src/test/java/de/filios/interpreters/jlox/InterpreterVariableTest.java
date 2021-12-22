package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterVariableTest extends TestStandardOutErr {

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
    void simpleVariableAssignment() {
        List<Stmt> statements = scanAndParse("var a=1;print a;");
        new Interpreter().interpret(statements);
        assertEquals("1\n", getOutputStreamAndTearDown());
    }

    @Test
    void simpleVariableOperation() {
        List<Stmt> statements = scanAndParse("var a=1;var b=2;print a+b;");
        new Interpreter().interpret(statements);
        assertEquals("3\n", getOutputStreamAndTearDown());
    }

    @Test
    void simpleVariableReAssignment() {
        List<Stmt> statements = scanAndParse("var a=1;var b=2;a=3;print a+b;");
        new Interpreter().interpret(statements);
        assertEquals("5\n", getOutputStreamAndTearDown());
    }


    @Test
    void undefinedVariableAssignment() {
        List<Stmt> statements = scanAndParse("var a=1;var b=2;c=3;print a+b;");
        new Interpreter().interpret(statements);
        assertEquals("c Undefined variable 'c'.\n[line 1]\n", getErrorStreamAndTearDown());
    }
}