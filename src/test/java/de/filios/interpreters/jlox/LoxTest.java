package de.filios.interpreters.jlox;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import com.ginsberg.junit.exit.FailOnSystemExit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;

class LoxTest {


    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();

    private static String simpleJloxFile = "src/test/ressources/simple.lox";

    public void setUpOuputStreamCaption() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    public void tearDownOuputStreamCaption() {
        System.setOut(standardOut);
    }

    public void setUpErrorStreamCaption() {
        System.setErr(new PrintStream(errorStreamCaptor));
    }

    public void tearDownErrorStreamCaption() {
        System.setErr(standardErr);
    }

    @org.junit.jupiter.api.Test
    @ExpectSystemExitWithStatus(64)
    void testToManyArguments(){
        this.setUpOuputStreamCaption();
        try {
            Lox.main(new String[]{"Hello", "Dello"});
            assertEquals("Usage: jlox [script]", outputStreamCaptor.toString());
        } catch (IOException e) {
            assertTrue(false," Unexpected IO Exception Thrown");
        }  finally {
             this.tearDownOuputStreamCaption();
        }
    }

    @org.junit.jupiter.api.Test
    @FailOnSystemExit
    void testNoFileAsArgument(){
        assertThrows(IOException.class, () -> Lox.main(new String[]{"Hello"}), "IOException erwartet, dass File 'Hello' nicht existiert");
    }

    @org.junit.jupiter.api.Test
    void testPaths(){
       assertTrue(Files.exists(Path.of(simpleJloxFile)));
    }

    @org.junit.jupiter.api.Test
    @FailOnSystemExit
    void testRunFile(){
        this.setUpOuputStreamCaption();
        try {
            Lox.main(new String[]{simpleJloxFile});
            assertEquals("class Simple {\n}\n", outputStreamCaptor.toString());
        } catch (IOException e) {
            assertTrue(false, e.getClass().getCanonicalName() + ": " + e.getMessage() );
        } finally {
            this.tearDownOuputStreamCaption();
        }
    }

    @org.junit.jupiter.api.Test
    void testErrorMessage(){

        this.setUpErrorStreamCaption();
        Lox.error(10,"int variable wrong declared");
        assertEquals("[line 10] Error : int variable wrong declared\n",errorStreamCaptor.toString());
        this.tearDownErrorStreamCaption();

    }

}