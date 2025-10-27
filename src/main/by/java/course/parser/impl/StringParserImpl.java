package main.by.java.course.parser.impl;

import main.by.java.course.exception.MyArrayException;
import main.by.java.course.parser.StringParser;
import main.by.java.course.validator.MyArrayValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringParserImpl implements StringParser {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public String[] parseString(String string) throws MyArrayException {
        logger.debug("Splitting string into array: '{}'", string);

        if (string == null) {
            throw new MyArrayException("Input string cannot be null");
        }

        if (string.isBlank()) {
            throw new MyArrayException("Input string cannot be empty");
        }

        MyArrayValidator validator = new MyArrayValidator();

        if (!validator.validateString(string)) {
            throw new MyArrayException("Invalid string cannot be converted to array!");
        }

        String[] result = string.split(ELEMENT_DIVIDER_REGEX);
        logger.debug("Successfully split string into {} elements", result.length);

        return result;
    }
}
