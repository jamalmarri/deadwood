@echo off

if not exist out mkdir out
javac -d out src\*.java
xcopy /Q /Y src\*.fxml out\ >nul
xcopy /E /Q /Y src\img out\img\ >nul
cls
java -cp out/ Deadwood
rmdir /S /Q out\img\ >nul
del /S /Q out\*.class >nul
del /S /Q out\*.fxml >nul