package de.filios.interpreters.jlox;

import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import com.ginsberg.junit.exit.FailOnSystemExit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;

class JloxMainAppTest extends TestStandardOutErr {


    private static String simpleJloxFile = "src/test/resources/simple.lox";

    @org.junit.jupiter.api.Test
    @ExpectSystemExitWithStatus(64)
    void testToManyArguments() {
        this.setUpOuputStreamCaption();
        try {
            Lox.main(new String[]{"Hello", "Dello"});
            assertEquals("Usage: jlox [script]", outputStreamCaptor.toString());
        } catch (IOException e) {
            assertTrue(false, " Unexpected IO Exception Thrown");
        } finally {
            this.tearDownOuputStreamCaption();
        }
    }

    @org.junit.jupiter.api.Test
    @ExpectSystemExitWithStatus(66)
    void testFileNotExistingAsArgument() {
        try {
            Lox.main(new String[]{"Hello"});
        } catch (IOException e) {
            assertTrue(false, " Unexpected IO Exception Thrown");
        }
    }

    @org.junit.jupiter.api.Test
    void testPaths() {
        assertTrue(Files.exists(Path.of(simpleJloxFile)));
    }

    @org.junit.jupiter.api.Test
    @FailOnSystemExit
    void testRunFile() {
        this.setUpOuputStreamCaption();
        try {
            Lox.main(new String[]{simpleJloxFile});
            assertEquals("3\n", outputStreamCaptor.toString());
        } catch (IOException e) {
            assertTrue(false, e.getClass().getCanonicalName() + ": " + e.getMessage());
        } finally {
            this.tearDownOuputStreamCaption();
        }
    }

    @org.junit.jupiter.api.Test
    void testErrorMessage() {

        this.setUpErrorStreamCaption();
        Lox.error(10, "int variable wrong declared");
        assertEquals("[line 10] Error : int variable wrong declared\n", errorStreamCaptor.toString());
        this.tearDownErrorStreamCaption();

    }

}