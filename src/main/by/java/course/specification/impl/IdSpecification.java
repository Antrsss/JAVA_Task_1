package main.by.java.course.specification.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.specification.MyArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record IdSpecification(long id) implements MyArraySpecification {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean specify(MyArray myArray) {
        logger.info("Founding MyArray with id {}", id);
        return id == myArray.getId();
    }
}
