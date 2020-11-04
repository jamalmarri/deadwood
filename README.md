# Deadwood
### Implemented by Jamal Marri and Megan Theimer

## Steps to compile:

NOTE: If your version of Java does not include JavaFX, then you will need to install OpenJFX and
add: '--module-path /path/to/openjfx/lib/ --add-modules javafx.controls,javafx.fxml,javafx.swing'
to the Java Virtual Machine's arguments at compile and runtime.

1. Compile using 'mkdir -p out;javac -d out/ src/\*.java;cp src/\*.fxml out/;cp -r src/img out/' at root of repository.
2. Run using 'java -cp out/ Deadwood' at root of repository.
