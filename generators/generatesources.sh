#/bin/zsh
javac src/de/filios/interpreter/jlox/generator/GenerateAst.java -d ../target/classes
java -cp ../target/classes de.filios.interpreter.jlox.generator.GenerateAst ../src/main/java
