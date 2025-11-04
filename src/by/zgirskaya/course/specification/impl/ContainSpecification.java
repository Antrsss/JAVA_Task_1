package by.zgirskaya.course.specification.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.specification.CustomArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record ContainSpecification(String customArrayElement) implements CustomArraySpecification {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean specify(CustomArray customArray) {
        String[] array = customArray.getMyArray();

        for (var string : array) {
            if (string.equals(customArrayElement)) {
                logger.info("Found matching element '{}' in MyArray id {}",
                        customArrayElement, customArray.getId());
                return true;
            }
        }

        logger.debug("Element '{}' not found in MyArray id {}",
                customArrayElement, customArray.getId());
        return false;
    }
}
