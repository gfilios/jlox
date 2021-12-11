package de.filios.interpreters.jlox;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();

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

    /*
        LEFT_PAREN , RIGHT_PAREN , LEFT_BRACE , RIGHT_BRACE ,
        COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,
        // One or two character tokens.
        BANG , BANG_EQUAL ,
        EQUAL, EQUAL_EQUAL,
        GREATER, GREATER_EQUAL,
        LESS, LESS_EQUAL,
     */
    @Test
    void scanSimpleTokens() {

        List<Token> expectedResult = new ArrayList();
        expectedResult.add(new Token(TokenType.LEFT_PAREN, "(", null, 0));
        expectedResult.add(new Token(TokenType.RIGHT_PAREN, ")", null, 0));
        expectedResult.add(new Token(TokenType.LEFT_BRACE, "{", null, 0));
        expectedResult.add(new Token(TokenType.RIGHT_BRACE, "}", null, 0));
        expectedResult.add(new Token(TokenType.COMMA, ",", null, 0));
        expectedResult.add(new Token(TokenType.DOT, ".", null, 0));
        expectedResult.add(new Token(TokenType.MINUS, "-", null, 0));
        expectedResult.add(new Token(TokenType.PLUS, "+", null, 0));
        expectedResult.add(new Token(TokenType.SEMICOLON, ";", null, 0));
        expectedResult.add(new Token(TokenType.SLASH, "/", null, 0));
        expectedResult.add(new Token(TokenType.STAR, "*", null, 0));
        expectedResult.add(new Token(TokenType.BANG, "!", null, 0));
        expectedResult.add(new Token(TokenType.BANG_EQUAL, "!=", null, 0));
        expectedResult.add(new Token(TokenType.EQUAL, "=", null, 0));
        expectedResult.add(new Token(TokenType.EQUAL_EQUAL, "==", null, 0));
        expectedResult.add(new Token(TokenType.GREATER, ">", null, 0));
        expectedResult.add(new Token(TokenType.GREATER_EQUAL, ">=", null, 0));
        expectedResult.add(new Token(TokenType.LESS, "<", null, 0));
        expectedResult.add(new Token(TokenType.LESS_EQUAL, "<=", null, 0));
        expectedResult.add(new Token(TokenType.EOF, "", null, 0));

        Scanner scanner = new Scanner("(){},.-+;/*!!= = == > >= < <=");
        List<Token> scannedTokens = scanner.scanTokens();
        assertArrayEquals(expectedResult.toArray(), scannedTokens.toArray());
    }

    @Test
    void scanStringTokens() {

        List<Token> expectedResult = new ArrayList();
        expectedResult.add(new Token(TokenType.LESS_EQUAL, "<=", null, 0));
        expectedResult.add(new Token(TokenType.STRING, "\"Georg Filios\"", null, 0));
        expectedResult.add(new Token(TokenType.SEMICOLON, ";", null, 0));
        expectedResult.add(new Token(TokenType.EOF, "", null, 0));


        Scanner scanner = new Scanner("<=\"Georg Filios\";");
        List<Token> scannedTokens = scanner.scanTokens();
        assertArrayEquals(expectedResult.toArray(), scannedTokens.toArray());
    }

    @Test
    void scanUnexpectedTokens() {

        setUpErrorStreamCaption();

        Scanner scanner = new Scanner("#%");
        List<Token> scannedTokens = scanner.scanTokens();

        String  expectedErrorMessages =
                "[line 1] Error : Unexpected character - #\n" +
                "[line 1] Error : Unexpected character - %\n";
        assertEquals(expectedErrorMessages, errorStreamCaptor.toString());
        tearDownErrorStreamCaption();
    }

    @Test
    void scanUnterminatedString() {

        setUpErrorStreamCaption();

        Scanner scanner = new Scanner(",.()\n\"GeorgFilios\nHans");
        List<Token> scannedTokens = scanner.scanTokens();

        String  expectedErrorMessages = "[line 3] Error : Unterminated String\n" ;
        assertEquals(expectedErrorMessages, errorStreamCaptor.toString());
        tearDownErrorStreamCaption();
    }

    @Test
    void scanDigitsTokens() {

        List<Token> expectedResult = new ArrayList();
        expectedResult.add(new Token(TokenType.NUMBER, "10", Double.parseDouble("10"), 0));
        expectedResult.add(new Token(TokenType.EQUAL, "=", null, 0));
        expectedResult.add(new Token(TokenType.NUMBER, "92.29", Double.parseDouble("92.29"), 0));
        expectedResult.add(new Token(TokenType.EOF, "", null, 0));


        Scanner scanner = new Scanner("10=92.29");
        List<Token> scannedTokens = scanner.scanTokens();
        assertArrayEquals(expectedResult.toArray(), scannedTokens.toArray());
    }

    @Test
    void scanIdentifier() {

        List<Token> expectedResult = new ArrayList();
        expectedResult.add(new Token(TokenType.IDENTIFIER, "hans", null, 0));
        expectedResult.add(new Token(TokenType.EQUAL, "=", null, 0));
        expectedResult.add(new Token(TokenType.NUMBER, "92.29", Double.parseDouble("92.29"), 0));
        expectedResult.add(new Token(TokenType.OR, "or", null, 0));
        expectedResult.add(new Token(TokenType.PRINT, "print", null, 0));
        expectedResult.add(new Token(TokenType.EOF, "", null, 0));


        Scanner scanner = new Scanner("hans=92.29 or print");
        List<Token> scannedTokens = scanner.scanTokens();
        assertArrayEquals(expectedResult.toArray(), scannedTokens.toArray());
    }

    @Test
    void scanTernaryTokens (){

        List<Token> expectedResult = new ArrayList();
        expectedResult.add(new Token(TokenType.QUESTIONMARK, "?", null, 0));
        expectedResult.add(new Token(TokenType.COLON, ":", null, 0));
        expectedResult.add(new Token(TokenType.EOF, "", null, 0));

        Scanner scanner = new Scanner("?:");
        List<Token> scannedTokens = scanner.scanTokens();
        assertArrayEquals(expectedResult.toArray(), scannedTokens.toArray());
    }
}