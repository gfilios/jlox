package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassTest extends TestStandardOutErr {

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
    void testSimpleClass() {
        StringBuilder program = new StringBuilder();
        program.append("class humbledumple {");
        program.append("    id () {");
        program.append("        return 1;");
        program.append("    }");
        program.append("}");

        program.append("print humbledumple;");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("humbledumple\n", getOutputStreamAndTearDown());

    }

}