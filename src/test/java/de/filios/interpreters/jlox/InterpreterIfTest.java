package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterIfTest extends TestStandardOutErr {

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
    void testIfLeftHand() {
        StringBuilder program = new StringBuilder();
        program.append("if (1<2) print \"georg\"; else print \"Elias\";");
        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", errorStreamCaptor.toString());
        assertEquals("georg\n", outputStreamCaptor.toString());

    }

    @Test
    void testIfElseHand() {
        StringBuilder program = new StringBuilder();
        program.append("if (1>2) print \"georg\"; else print \"Elias\";");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", errorStreamCaptor.toString());
        assertEquals("Elias\n", outputStreamCaptor.toString());

    }

    @Test
    void testIfBlockLeft() {
        StringBuilder program = new StringBuilder();
        program.append("var a = 8;");
        program.append("var b = 4;");

        program.append("if (a+b>10) { print \"georg\"; print \"Elias\";} else { a = 4;print a; print \"Elias\";}");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", errorStreamCaptor.toString());
        assertEquals("georg\nElias\n", outputStreamCaptor.toString());

    }

    @Test
    void testIfBlockRight() {
        StringBuilder program = new StringBuilder();
        program.append("var a = 8;");
        program.append("var b = 4;");
        program.append("if (a+b<2) { print \"georg\"; print \"Elias\";} else { var a = 4;print a; print \"Elias\";}");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", errorStreamCaptor.toString());
        assertEquals("4\nElias\n", outputStreamCaptor.toString());
    }

    @Test
    void testIfBlockVariableScope() {
        StringBuilder program = new StringBuilder();
        program.append("var a = 8;");
        program.append("if (a<2) ");
        program.append("{ ");
        program.append("    print \"georg\";");
        program.append("} ");
        program.append("else ");
        program.append("{ ");
        program.append("    a = 4;");
        program.append("    print a; ");
        program.append("}");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", errorStreamCaptor.toString());
        assertEquals("4\n", outputStreamCaptor.toString());
    }

}