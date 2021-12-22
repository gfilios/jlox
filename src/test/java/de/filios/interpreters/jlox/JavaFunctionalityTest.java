package de.filios.interpreters.jlox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaFunctionalityTest extends TestStandardOutErr {

    @Test
    public void testBreakBehaviour(){
        int i = 0;
        for (i=2;i<10;i++) {
            if (i==5) break;
        }
        assertEquals(5,i);
    }



}