package by.zgirskaya.course.specification.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.specification.CustomArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public record IdSpecification(long id) implements CustomArraySpecification {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean specify(CustomArray customArray) {
        logger.info("Founding MyArray with id {}", id);
        return id == customArray.getId();
    }
}
