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

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("2\n", getOutputStreamAndTearDown());

    }

    @Test
    void testSimpleLoopExplicitDeclaration() {
        StringBuilder program = new StringBuilder();
        program.append("for (var i=2;i<3;i=i+1){");
        program.append("    print i;");
        program.append("}");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("2\n", getOutputStreamAndTearDown());

    }

    @Test
    void testSimpleLoopExplicitDeclarationInAndOut() {
        StringBuilder program = new StringBuilder();
        program.append("var i=10;");
        program.append("for (var i=2;i<3;i=i+1){");
        program.append("    print i;");
        program.append("}");
        program.append("print 10;");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("2\n10\n", getOutputStreamAndTearDown());

    }

}