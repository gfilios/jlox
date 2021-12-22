package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterBlockTest extends TestStandardOutErr {
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
    void testSimpleBlock() {
        StringBuilder program = new StringBuilder();
        program.append("var a = \"global a\" ;");
        program.append("var b = \"global b\" ;");
        program.append("var c = \"global c\" ;");
        program.append("{");
        program.append("    var a = \"outer a\" ;");
        program.append("    var b = \"outer b\" ;");
        program.append("    {");
        program.append("        var a = \"inner a\";");
        program.append("        print a;");
        program.append("        print b;");
        program.append("        print c;");
        program.append("    }");
        program.append("    print a;");
        program.append("    print b;");
        program.append("    print c;");
        program.append("}");
        program.append("print a;");
        program.append("print b;");
        program.append("print c;");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("inner a\nouter b\nglobal c\nouter a\nouter b\nglobal c\nglobal a\nglobal b\nglobal c\n",
                getOutputStreamAndTearDown());

    }

    @Test
    void testOuterReassignedInInner() {
        StringBuilder program = new StringBuilder();
        program.append("var a = 1;");
        program.append("{");
        program.append("    var a = a + 2 ;");
        program.append("    print a;");
        program.append("}");
        program.append("print a;");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("",getErrorStreamAndTearDown());
        assertEquals("3\n1\n", getOutputStreamAndTearDown());

    }

}