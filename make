#!/bin/bash

javac -d out/ src/*
clear
java -cp out/ Deadwood
rm out/*.class
