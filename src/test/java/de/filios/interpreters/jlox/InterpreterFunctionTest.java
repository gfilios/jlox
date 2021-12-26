package de.filios.interpreters.jlox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterFunctionTest extends TestStandardOutErr {

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
    void testSimpleFun() {
        StringBuilder program = new StringBuilder();
        program.append("fun sayHi(first, last) {");
        program.append("    print \"Hi \" + first +  \" \" + last + \"!\";");
        program.append("}");
        program.append("sayHi(\"Georg\", \"Filios\");");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("Hi Georg Filios!\n", getOutputStreamAndTearDown());

    }


    @Test
    void testParameterScope() {
        StringBuilder program = new StringBuilder();
        program.append("fun add(x) {");
        program.append("    x = x + 1;");
        program.append("    print x;");
        program.append("}");
        program.append("var x = 1;");
        program.append("print x;");
        program.append("add(x);");
        program.append("print x;");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("", getErrorStreamAndTearDown());
        assertEquals("1\n2\n1\n", getOutputStreamAndTearDown());

    }

    @Test
    void testTooManyParameter() {
        StringBuilder program = new StringBuilder();
        program.append("fun add(x) {");
        program.append("    x = x + 1;");
        program.append("    print x;");
        program.append("}");
        program.append("var x = 1;");
        program.append("add(x,x);");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals(") Expected 1 arguments but got 2.\n[line 1]\n", getErrorStreamAndTearDown());

    }


    @Test
    void testSimpleReturn() {
        StringBuilder program = new StringBuilder();
        program.append("fun constantvalue() {");
        program.append("  return 3;");
        program.append("}");
        program.append("print constantvalue();");


        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("3\n", getOutputStreamAndTearDown());

    }


    @Test
    void testRecursive() {
        StringBuilder program = new StringBuilder();
        program.append("fun fib(n) {");
        program.append("    if (n<=1) return 1;");
        program.append("    else return fib(n-2)+fib(n-1);");
        program.append("}");
        program.append("for (var i=0;i<10;i=i+1) {");
        program.append("    print fib(i);");
        program.append("}");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("1\n" +
                "1\n" +
                "2\n" +
                "3\n" +
                "5\n" +
                "8\n" +
                "13\n" +
                "21\n" +
                "34\n" +
                "55\n", getOutputStreamAndTearDown());
    }


    @Test
    void testClosure() {
        StringBuilder program = new StringBuilder();
        program.append("fun makeCounter(){");
        program.append("    var i=0;");
        program.append("    fun count() {");
        program.append("        i = i + 1;");
        program.append("        print i;");
        program.append("    }");
        program.append("    return count;");
        program.append("}");
        program.append("var counter = makeCounter();");
        program.append("counter();");
        program.append("counter();");
        program.append("counter();");

        List<Stmt> statements = scanAndParse(program.toString());
        new Interpreter().interpret(statements);
        assertEquals("1\n2\n3\n", getOutputStreamAndTearDown());

    }

}
