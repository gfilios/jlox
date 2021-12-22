package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterWhileTest extends TestStandardOutErr {


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
    void testSimpleAnd() {
        StringBuilder program = new StringBuilder();
        program.append("var i=1;");
        program.append("while (i<10) {");
        program.append("     print i;");
        program.append("     i=i+1;");
        program.append("}");



        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        String resultError =  getErrorStreamAndTearDown();
        assertEquals("",resultError,"Unexpected Error" + resultError);
        assertEquals("1\n2\n3\n4\n5\n6\n7\n8\n9\n", getOutputStreamAndTearDown());
    }
}