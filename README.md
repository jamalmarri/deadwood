# Deadwood
### Implemented by Jamal Marri and Megan Theimer

## Steps to compile:

### Option A:
NOTE: If your version of Java does not include JavaFX, then add:
'--module-path /usr/share/openjfx/lib/ --add-modules javafx.controls,javafx.fxml,javafx.swing'
to the Java Virtual Machine's arguments at compile and runtime.

1. Simply compile yourself using 'mkdir -p out;javac -d out/ src/*' at root of repository.
2. Run using 'java -cp out/ Deadwood' at root of repository.

### Option B:
1. Run the "make" script (./make) or "make.bat" batch file depending on your operating system at the root of the repository.
2. The program will compile the game, run it, and then delete the class files it just generated.
