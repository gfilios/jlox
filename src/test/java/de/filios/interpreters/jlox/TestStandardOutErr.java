package de.filios.interpreters.jlox;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class TestStandardOutErr {
    protected final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    protected final ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;

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

    protected List<Stmt> scanAndParse(String string) {
        Scanner scanner = new Scanner(string);
        List<Token> scannedTokens = scanner.scanTokens();
        Parser parser = new Parser(scannedTokens);
        List<Stmt> statements = parser.parse();
        return statements;
    }
}
