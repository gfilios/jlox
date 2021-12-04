package de.filios.interpreters.jlox;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void testToString() {
        Token token = new Token(TokenType.COMMA, ",", new String("comma"), 10 );
        assertEquals(TokenType.COMMA + " , comma", token.toString() );

    }
}