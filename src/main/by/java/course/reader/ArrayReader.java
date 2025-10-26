package main.by.java.course.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Scanner;

public class ArrayReader {
    private static final String ELEMENT_REGEX = "\\s+";
    private static final Logger logger = LogManager.getLogger();

    public static String[] readArray(InputStream input) {
        Scanner scanner = new Scanner(input);
        String line = scanner.nextLine();
        line = line.trim();
        return line.split(ELEMENT_REGEX);
    }
}
