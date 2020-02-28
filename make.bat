if not exist out mkdir out
javac -d out\ src\*
xcopy /Q /Y src\*.uxf out\
xcopy /E /Q /Y src\img out\img\
cls
java -cp out/ Deadwood
