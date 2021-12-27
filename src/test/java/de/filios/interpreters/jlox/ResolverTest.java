package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResolverTest extends TestStandardOutErr {


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
    void testTopLevelReturn() {
        StringBuilder program = new StringBuilder();
        program.append("return;");

        parseResolveInterpret(program);
        assertEquals("[line 1] Error  at ': return' Can't return from top-level code.\n",getErrorStreamAndTearDown());
    }
}