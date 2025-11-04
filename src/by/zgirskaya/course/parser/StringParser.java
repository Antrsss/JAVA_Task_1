package by.zgirskaya.course.parser;

import by.zgirskaya.course.exception.CustomArrayException;

public interface StringParser {

    String ELEMENT_DIVIDER_REGEX = "\\s+";
    String ARRAY_DIVIDER_REGEX = "\\r?\\n";

    String[][] parseFile(String string) throws CustomArrayException;
    String[] parseLine(String string) throws CustomArrayException;
}
