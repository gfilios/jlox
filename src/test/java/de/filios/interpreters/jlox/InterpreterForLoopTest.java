package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterForLoopTest extends TestStandardOutErr {
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
    void testSimpleLoop() {
        StringBuilder program = new StringBuilder();
        program.append("var i=0;");
        program.append("for (i=2;i<3;i=i+1){");
        program.append("    print i;");
        program.append("}");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", errorStreamCaptor.toString());
        assertEquals("2\n", outputStreamCaptor.toString());

    }

    @Test
    void testSimpleLoopExplicitDeclaration() {
        StringBuilder program = new StringBuilder();
        program.append("for (var i=2;i<3;i=i+1){");
        program.append("    print i;");
        program.append("}");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", errorStreamCaptor.toString());
        assertEquals("2\n", outputStreamCaptor.toString());

    }

    @Test
    void testSimpleLoopExplicitDeclarationInAndOut() {
        StringBuilder program = new StringBuilder();
        program.append("var i=10;");
        program.append("for (var i=2;i<3;i=i+1){");
        program.append("    print i;");
        program.append("}");
        program.append("print 10;");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", errorStreamCaptor.toString());
        assertEquals("2\n10\n", outputStreamCaptor.toString());

    }

}