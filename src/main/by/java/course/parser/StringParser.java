package main.by.java.course.parser;

import main.by.java.course.exception.MyArrayException;

public interface StringParser {
    String ELEMENT_DIVIDER_REGEX = "\\s+";
    String ARRAY_DIVIDER_REGEX = "\\r?\\n";

    String[][] parseFile(String string) throws MyArrayException;
    String[] parseLine(String string) throws MyArrayException;
}
