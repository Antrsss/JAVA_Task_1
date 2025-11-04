package by.zgirskaya.course.reader;

import by.zgirskaya.course.exception.CustomArrayException;

public interface CustomStringReader {
    String readStringFromFile(String filePath) throws CustomArrayException;
}
