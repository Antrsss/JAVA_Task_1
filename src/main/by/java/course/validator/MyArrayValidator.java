package main.by.java.course.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyArrayValidator {
    private static final String VALID_STRING_REGEX = "([a-zA-Z]+\\s*)+";
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
