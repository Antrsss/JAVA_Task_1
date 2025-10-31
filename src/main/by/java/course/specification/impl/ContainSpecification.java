package main.by.java.course.specification.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.specification.MyArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record ContainSpecification(String myArrayElement) implements MyArraySpecification {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean specify(MyArray myArray) {
        String[] array = myArray.getMyArray();

        for (var string : array) {
            if (string.equals(myArrayElement)) {
                logger.info("Found matching element '{}' in MyArray id {}",
                        myArrayElement, myArray.getId());
                return true;
            }
        }

        logger.debug("Element '{}' not found in MyArray id {}",
                myArrayElement, myArray.getId());
        return false;
    }
}
