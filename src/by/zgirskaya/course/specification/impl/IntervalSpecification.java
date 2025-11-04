package by.zgirskaya.course.specification.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.specification.CustomArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record IntervalSpecification(int minValue, int maxValue) implements CustomArraySpecification {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean specify(CustomArray customArray) {
        String[] array = customArray.getMyArray();
        int currentValue;

        logger.debug("Checking {} elements of MyArray id {} for range [{}, {}]",
                array.length, customArray.getId(), minValue, maxValue);

        for (int i = 0; i < array.length; i++) {
            currentValue = customArray.calculateArrayElementAtIndexOf(i);

            if (currentValue < minValue || currentValue > maxValue) {
                logger.debug("Element '{}' is above maximum {} or under minimum {} in MyArray id {}",
                        currentValue, maxValue, minValue, customArray.getId());
                return false;
            }
        }
        return true;
    }
}
