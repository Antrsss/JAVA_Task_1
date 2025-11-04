package by.zgirskaya.course.repository;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.specification.CustomArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CustomArrayRepositoryImpl {

    private static final Logger logger = LogManager.getLogger();

    private final List<CustomArray> customArrays = new ArrayList<>();

    public boolean addMyArray(CustomArray customArray) {
        logger.debug("Attempting to add MyArray to repository: {}",
                customArray != null ? "id=" + customArray.getId() : "null");

        return customArrays.add(customArray);
    }

    public boolean removeMyArray(CustomArray customArray) {
        logger.debug("Attempting to remove MyArray from repository: {}",
                customArray != null ? "id=" + customArray.getId() : "null");

        return customArrays.remove(customArray);
    }

    public List<CustomArray> sort(Comparator<? super CustomArray> c) {
        logger.debug("Starting sort operation with comparator: {}",
                c != null ? c.getClass().getSimpleName() : "null");

        if (c == null) {
            logger.warn("Cannot sort with null comparator");
            return new ArrayList<>(customArrays);
        }

        var myArraysCopy = new ArrayList<>(customArrays);
        myArraysCopy.sort(c);
        return myArraysCopy;
    }

    public List<CustomArray> query(CustomArraySpecification specification) {
        logger.debug("Starting query operation with specification: {}",
                specification != null ? specification.getClass().getSimpleName() : "null");

        if (specification == null) {
            logger.warn("Cannot query with null specification, returning all arrays");
            return new ArrayList<>(customArrays);
        }

        return customArrays.stream().filter(specification::specify).toList();
    }
}
