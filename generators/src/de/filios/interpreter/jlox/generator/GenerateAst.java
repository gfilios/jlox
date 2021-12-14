package de.filios.interpreter.jlox.generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GenerateAst {



    public static void main(String[] args) throws IOException {
            // "./src/main/java";
        String outputDir = args[0];

        List<String> exprAstDefinition = Arrays.asList(
                "Assign       -> Token name, Expr value",
                "Binary       -> Expr left, Token operator, Expr right",
                "Grouping     -> Expr expression",
                "Literal      -> Object value",
                "Logical      -> Expr left, Token operator, Expr right",
                "Unary        -> Token operator, Expr right",
                "Variable     -> Token name"
        );

        generateAstClass(outputDir, "Expr", "de.filios.interpreters.jlox", exprAstDefinition);


        /**
         * program → statement * EOF ;
         * statement → exprStmt | printStmt ;
         * exprStmt → expression ";" ;
         * printStmt → "print" expression ";" ;
         *
         *
         */
        List<String> stmtAstDefinition = Arrays.asList(
                "Block          -> List<Stmt> statements",
                "Expression     -> Expr expression",
                "If             -> Expr condition, Stmt thenBranch, Stmt elseBranch",
                "Print          -> Expr expression",
                "Var            -> Token name, Expr initializer"
        );
        generateAstClass(outputDir, "Stmt", "de.filios.interpreters.jlox", stmtAstDefinition);


    }

    private static void generateAstClass(String outputDir, String baseName, String packageName, List<String> astDefinition) throws IOException {

        String packageDir = packageName.replace('.', '/');
        Path fullPath = Path.of(outputDir + "/" + packageDir + "/" + baseName + ".java");
        System.out.println("Writing To: " + fullPath.toAbsolutePath().toString());
        PrintWriter printWriter = new PrintWriter(fullPath.toFile(), StandardCharsets.UTF_8);

        printClassHeader(baseName, packageName, printWriter);
        printVisitorInterface(printWriter, baseName, astDefinition);
        printSubclasses(baseName, astDefinition, printWriter);
        printClassTail(printWriter);

    }



    private static void printClassTail(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println("}");
        printWriter.close();
    }

    private static void printClassHeader(String baseName, String packageName, PrintWriter printWriter) {
        printWriter.println("package " + packageName + ";");
        printWriter.println();
        printWriter.println("import java.util.List;");
        printWriter.println("import de.filios.interpreters.jlox.Token;");
        printWriter.println();
        printWriter.println("abstract class " + baseName + " {");
        printWriter.println();
        printWriter.println("\tabstract <R> R accept (Visitor<R> visitor);");
        printWriter.println();

    }

    private static void printVisitorInterface(PrintWriter printWriter, String baseName, List<String> astDefinition) {
        printWriter.println();
        printWriter.println("\tinterface Visitor<R> {");
        astDefinition.stream().forEach((rule) -> {
            String type = rule.split("->")[0].trim();
            printWriter.println("\t\t R visit" + type + baseName + "( " + type + " " + baseName.toLowerCase(Locale.ROOT) + ");");
        });
        printWriter.println("\t}");
    }

    private static void printSubclasses(String baseName, List<String> astDefinition, PrintWriter printWriter) {
        astDefinition.stream().forEach((rule) -> printSubclass(printWriter, baseName, rule));
    }

    private static void printSubclass(PrintWriter printWriter, String baseName, String rule) {
        printWriter.println();
        printWriter.println("\t//    " + rule);

        String[] leftAndRight = rule.split("->");
        String className = leftAndRight[0];

        printWriter.println("\tstatic class " + className.strip() + " extends " + baseName + " {");
        printWriter.println();

        printSubclassConstructor(printWriter, leftAndRight, className);
        printSublcassInstanceVariables(printWriter, leftAndRight);
        printSublassAcceptOverride(printWriter, className, baseName);

        printWriter.println("\t}");

    }

    private static void printSublassAcceptOverride(PrintWriter printWriter, String className, String baseName){
        printWriter.println();
        printWriter.println("\t\t@Override");
        printWriter.println("\t\t<R> R accept(Visitor<R> visitor) {");
        printWriter.println("\t\t\treturn visitor.visit"+className.trim()+baseName.trim()+"(this);");
        printWriter.println("\t\t}");
    }

    private static void printSublcassInstanceVariables(PrintWriter printWriter, String[] leftAndRight) {
        String[] variables = leftAndRight[1].split(",");
        for (String variable : variables) {
            String[] typeAndName = variable.trim().split(" ");
            String type = typeAndName[0].trim();
            String name = typeAndName[1].trim();
            printWriter.println("\t\t" + "final " + type + " " + name + ";");
        }
    }

    private static void printSubclassConstructor(PrintWriter printWriter, String[] leftAndRight, String className) {
        printWriter.println("\t\t" + className.strip() + "( " + leftAndRight[1].trim() + ") {");
        String[] variables = leftAndRight[1].split(",");
        for (String variable : variables) {
            String[] typeAndName = variable.trim().split(" ");
            String type = typeAndName[0].trim();
            String name = typeAndName[1].trim();
            printWriter.println("\t\t\t" + "this." + name + " = " + name + ";");
        }
        printWriter.println("\t\t}");
    }


}
