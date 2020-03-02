#!/bin/bash

mkdir -p out
javac --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls,javafx.fxml,javafx.swing -d out src/*.java
cp src/*.fxml out/
cp -r src/img out/
clear
java --module-path /usr/share/openjfx/lib/ --add-modules javafx.controls,javafx.fxml,javafx.swing -cp out Deadwood
