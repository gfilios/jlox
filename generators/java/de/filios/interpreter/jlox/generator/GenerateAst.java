package de.filios.interpreter.jlox.generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {

    private static final String outputDir = "./src/main/java";

    public static void main(String[] args) throws IOException {
        List<String> astDefinition = Arrays.asList(
                "Binary       -> Expr left, Token operator, Expr right",
                "Grouping     -> Expr expression",
                "Literal      -> Object value",
                "Unary        -> Token operator, Expr right"
        );


        generateAstClass("Expr", "de.filios.interpreters.jlox.ast", astDefinition);
    }

    private static void generateAstClass(String baseName, String packageName, List<String> astDefinition) throws IOException{

        String packageDir = packageName.replace('.','/');
        Path fullPath = Path.of(outputDir + "/" + packageDir + "/" + baseName + ".java");

        PrintWriter printWriter = new PrintWriter(fullPath.toFile(), StandardCharsets.UTF_8);

        printClassHeader(baseName, packageName, printWriter);

        astDefinition.stream().forEach((rule)->printConcreteClass(printWriter,baseName, rule));

        printClassTail(printWriter);

    }

    private static void printConcreteClass(PrintWriter printWriter, String baseName, String rule) {
        printWriter.println();
        printWriter.println("\t/**");
        printWriter.println("\t    Generated Class from following rule");
        printWriter.println("\t    "+ rule);
        printWriter.println("\t**/");

        String[] leftAndRight = rule.split("->");
        String className = leftAndRight[0];

        printWriter.println("\tpublic static class "+ className.strip() + " extends " + baseName + " {");
        printWriter.println();
        printWriter.println("\t\t"+ className.strip() + "( " + leftAndRight[1].trim() + ") {");
        String[] variables =leftAndRight[1].split(",");
        for (String variable : variables) {
            String [] typeAndName = variable.trim().split(" ");
            String type = typeAndName[0].trim();
            String name = typeAndName[1].trim();
            printWriter.println("\t\t\t" + "this." + name + " = " + name + ";");
        }
        printWriter.println("\t\t}");
        for (String variable : variables) {
            String [] typeAndName = variable.trim().split(" ");
            String type = typeAndName[0].trim();
            String name = typeAndName[1].trim();
            printWriter.println("\t\t" + "final " + type + " " + name + ";");
        }

        printWriter.println("\t}");

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
    }
}
