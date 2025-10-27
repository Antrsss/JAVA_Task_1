package main.by.java.course.reader;

import main.by.java.course.exception.MyArrayException;

interface StringReader {
    String readString(String filePath) throws MyArrayException;
}
