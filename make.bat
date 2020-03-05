@echo off

if not exist out mkdir out
javac -d out src\*.java
xcopy /Q /Y src\*.fxml out\
xcopy /E /Q /Y src\img out\img\
cls
java -cp out/ Deadwood
