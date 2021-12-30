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


    @Test
    void testInitCall() {
        StringBuilder program = new StringBuilder();
        program.append("class humbledumple {");
        program.append("    init () {");
        program.append("       print \"Georg\";");
        program.append("    }");
        program.append("}");
        program.append("var a = humbledumple();");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("Georg\n", getOutputStreamAndTearDown());

    }

    @Test
    void testInitVariableInit() {
        StringBuilder program = new StringBuilder();
        program.append("class humbledumple {");
        program.append("    init () {");
        program.append("        this.name = \"Georg\";");
        program.append("    }");
        program.append("}");
        program.append("var a = humbledumple();");
        program.append("print a.name;");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("Georg\n", getOutputStreamAndTearDown());

    }

    @Test
    void testSuperclass() {
        StringBuilder program = new StringBuilder();
        program.append("class Doughnut {");
        program.append("    cook () {");
        program.append("        print \"Fry\";");
        program.append("    }");
        program.append("}");
        program.append("class BostonCream < Doughnut {}");

        program.append("var a = BostonCream();");
        program.append("a.cook();");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("Fry\n", getOutputStreamAndTearDown());

    }

    @Test
    void testSuperclassCascade() {
        StringBuilder program = new StringBuilder();
        program.append("class Doughnut {");
        program.append("    cook () {");
        program.append("        print \"Fry until golden brown.\";");
        program.append("    }");
        program.append("}");
        program.append("class BostonCream < Doughnut {");
        program.append("    cook () {");
        program.append("        super.cook();");
        program.append("        print \"Pipe full of custard and coat with chocoloate.\";");
        program.append("    }");
        program.append("}");
        program.append("var a = BostonCream();");
        program.append("a.cook();");

        parseResolveInterpret(program);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals(
                "Fry until golden brown.\n" +
                "Pipe full of custard and coat with chocoloate.\n",
                getOutputStreamAndTearDown());

    }

    @Test
    void testSuperWithoutInheritancelass() {
        StringBuilder program = new StringBuilder();
        program.append("class Doughnut {");
        program.append("}");
        program.append("class BostonCream { cook() { super.cook();}}");

        program.append("var a = BostonCream();");
        program.append("a.cook();");

        parseResolveInterpret(program);
        assertEquals("[line 1] Error  at ': super' Can't use 'super' in a class without super class.\n", getErrorStreamAndTearDown());

    }

    @Test
    void testSuperOutsideclass() {
        StringBuilder program = new StringBuilder();
        program.append("class Doughnut {");
        program.append("}");

        program.append("super.cook();");

        parseResolveInterpret(program);
        assertEquals("[line 1] Error  at ': super' Can't use 'super' outside a class.\n", getErrorStreamAndTearDown());

    }

}