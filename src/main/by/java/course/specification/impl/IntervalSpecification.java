package main.by.java.course.specification.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.specification.MyArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record IntervalSpecification(int minValue, int maxValue) implements MyArraySpecification {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean specify(MyArray myArray) {
        String[] array = myArray.getMyArray();
        int currentValue;

        logger.debug("Checking {} elements of MyArray id {} for range [{}, {}]",
                array.length, myArray.getId(), minValue, maxValue);

        for (var string : array) {
            currentValue = calculateArrayElement(string);

            if (currentValue < minValue || currentValue > maxValue) {
                logger.debug("Element '{}' is above maximum {} or under minimum {} in MyArray id {}",
                        currentValue, maxValue, minValue, myArray.getId());
                return false;
            }
        }
        return true;
    }

    private int calculateArrayElement(String str) {
        int value = 0;
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                value += c;
            } else if (c >= 'a' && c <= 'z') {
                value -= c;
            }
        }

        return value;
    }
}
