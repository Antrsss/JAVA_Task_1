package main.by.java.course.creator;

import main.by.java.course.exception.MyArrayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayCreator {
    private static final String ELEMENT_REGEX = "\\s+";

    private static final Logger logger = LogManager.getLogger();

    public String[] createArray(String string) throws MyArrayException {
        logger.debug("Splitting string into array: '{}'", string);

        if (string == null) {
            throw new MyArrayException("Input string cannot be null");
        }

        if (string.isBlank()) {
            logger.warn("Input string is blank, returning empty array");
            return new String[0];
        }

        String[] result = string.split(ELEMENT_REGEX);
        logger.debug("Successfully split string into {} elements", result.length);

        return result;
    }
}
