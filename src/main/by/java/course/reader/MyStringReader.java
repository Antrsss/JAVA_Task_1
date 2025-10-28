package main.by.java.course.reader;

import main.by.java.course.exception.MyArrayException;

public interface MyStringReader {
    String readStringFromFile(String filePath) throws MyArrayException;
}
