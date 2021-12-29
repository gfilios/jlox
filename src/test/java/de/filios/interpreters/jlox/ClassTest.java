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
        program.append("class humbledumple {}");
        program.append("print humbledumple;");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("humbledumple\n", getOutputStreamAndTearDown());

    }

    @Test
    void testClassMethod() {
        StringBuilder program = new StringBuilder();
        program.append("class humbledumple {");
        program.append("    id () {");
        program.append("        return 1;");
        program.append("    }");
        program.append("}");
        program.append("var a = humbledumple();");

        program.append("print a.id();");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("1\n", getOutputStreamAndTearDown());

    }


    @Test
    void testThisClassAccess() {
        StringBuilder program = new StringBuilder();
        program.append("class humbledumple {");
        program.append("    getName () {");
        program.append("        return this.name;");
        program.append("    }");
        program.append("}");
        program.append("var a = humbledumple();");
        program.append("a.name = 1;");

        program.append("print a.getName();");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("1\n", getOutputStreamAndTearDown());

    }

    @Test
    void testClassInstantiate() {
        StringBuilder program = new StringBuilder();
        program.append("class humbledumple {}");
        program.append("var a = humbledumple();");
        program.append("print a;");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("humbledumple instance\n", getOutputStreamAndTearDown());

    }

    @Test
    void testClassSetterGetter() {
        StringBuilder program = new StringBuilder();
        program.append("class humbledumple {}");
        program.append("var a = humbledumple();");
        program.append("a.name = \"Georg\";");
        program.append("a.number = 12;");
        program.append("print a.name;");
        program.append("print a.number;");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("Georg\n12\n", getOutputStreamAndTearDown());

    }

    @Test
    void testNoFieldsGetter() {
        StringBuilder program = new StringBuilder();
        program.append("class humbledumple {}");
        program.append("var a = humbledumple();");
        program.append("print a.name;");

        parseResolveInterpret(program);
        assertEquals("name Undefined property 'name'.\n[line 1]\n", getErrorStreamAndTearDown());

    }

}