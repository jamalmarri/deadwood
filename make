#!/bin/bash

mkdir -p out
javac -d out src/*.java
cp src/*.fxml out/
cp -r src/img out/
clear
java -cp out Deadwood
