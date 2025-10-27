package main.by.java.course.parser;

import main.by.java.course.exception.MyArrayException;

public interface StringParser {
    String ELEMENT_DIVIDER_REGEX = "\\s+";
    String[] parseString(String string) throws MyArrayException;
}
