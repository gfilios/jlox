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
    void testSimpleBoolean() {
        StringBuilder program = new StringBuilder();
        program.append(" true and true");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("",errorStreamCaptor.toString());
        assertEquals("true\n", outputStreamCaptor.toString());
    }
}