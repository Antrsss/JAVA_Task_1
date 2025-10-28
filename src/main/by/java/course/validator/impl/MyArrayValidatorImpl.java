package main.by.java.course.validator.impl;

import main.by.java.course.validator.MyArrayValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyArrayValidatorImpl implements MyArrayValidator {

    private static final Logger logger = LogManager.getLogger();

    public boolean validateString(String string) {
        logger.debug("Validating string: '{}'", string);

        if (string == null) {
            logger.warn("Validation failed: string is null");
            return false;
        }

        return string.matches(VALID_STRING_REGEX);
    }
}
