package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterperterLogicalTest extends TestStandardOutErr {


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
    void testSimpleAnd() {
        StringBuilder program = new StringBuilder();
        program.append("print (true and true);");
        program.append("print (true and false);");
        program.append("print (false and true);");
        program.append("print (false and false);");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("true\nfalse\nfalse\nfalse\n", getOutputStreamAndTearDown());
    }

    @Test
    void testSimpleOr() {
        StringBuilder program = new StringBuilder();
        program.append("print (true or true);");
        program.append("print (true or false);");
        program.append("print (false or true);");
        program.append("print (false or false);");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("true\ntrue\ntrue\nfalse\n", getOutputStreamAndTearDown());
    }

    @Test
    void testSimpleOrAnd() {
        StringBuilder program = new StringBuilder();
        program.append("print (true or true and false);");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        String errorResult = getErrorStreamAndTearDown();
        assertEquals("",errorResult, "Unexpected Error Thown:" + errorResult);
        assertEquals("true\n", getOutputStreamAndTearDown());
    }

    @Test
    void testStringBoolean() {
        StringBuilder program = new StringBuilder();
        program.append("print (\"georg\" or 2);");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("georg\n", getOutputStreamAndTearDown());
    }

    @Test
    void testNilBoolean() {
        StringBuilder program = new StringBuilder();
        program.append("print (nil or \"georg\" );");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("georg\n", getOutputStreamAndTearDown());
    }
}