package by.zgirskaya.course.validator.impl;

import by.zgirskaya.course.validator.CustomArrayValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomArrayValidatorImpl implements CustomArrayValidator {

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
