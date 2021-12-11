package de.filios.interpreters.jlox;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {


    @Test
    void parseSimpleExpression() {

        Scanner scanner = new Scanner("-123 * (45.67+37);");
        List<Token> scannedTokens = scanner.scanTokens();
        Parser parser = new Parser(scannedTokens);
        List<Stmt> statements = parser.parse();


        Stmt.Expression statement = (Stmt.Expression) statements.get(0);

        String result = new AstPrinter().print(statement.expression);
        //System.out.println(result);
        assertEquals("(* (- 123.0) (group (+ 45.67 37.0)))", result);


    }
}