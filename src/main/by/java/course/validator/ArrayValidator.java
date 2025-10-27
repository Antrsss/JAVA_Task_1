package main.by.java.course.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ArrayValidator {
    private static final String VALID_STRING_REGEX = "([a-zA-Z]+\\s*)+";
    private static final Logger logger = LogManager.getLogger();

    public static boolean validateString(String string) {
        return string.matches(VALID_STRING_REGEX);
    }
}
