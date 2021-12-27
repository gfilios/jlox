package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IfTest extends TestStandardOutErr {

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
        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("georg\n", getOutputStreamAndTearDown());

    }

    @Test
    void testIfElseHand() {
        StringBuilder program = new StringBuilder();
        program.append("if (1>2) print \"georg\"; else print \"Elias\";");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("Elias\n", getOutputStreamAndTearDown());

    }

    @Test
    void testIfBlockLeft() {
        StringBuilder program = new StringBuilder();
        program.append("var a = 8;");
        program.append("var b = 4;");

        program.append("if (a+b>10) { print \"georg\"; print \"Elias\";} else { a = 4;print a; print \"Elias\";}");


        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("georg\nElias\n", getOutputStreamAndTearDown());

    }

    @Test
    void testIfBlockRight() {
        StringBuilder program = new StringBuilder();
        program.append("var a = 8;");
        program.append("var b = 4;");
        program.append("if (a+b<2) { print \"georg\"; print \"Elias\";} else { var a = 4;print a; print \"Elias\";}");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("4\nElias\n", getOutputStreamAndTearDown());
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

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("4\n", getOutputStreamAndTearDown());
    }

}