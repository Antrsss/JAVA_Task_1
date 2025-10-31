package main.by.java.course.repository.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.repository.MyArrayRepository;
import main.by.java.course.specification.MyArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class MyArrayRepositoryImpl implements MyArrayRepository {

    private static final Logger logger = LogManager.getLogger();

    private final List<MyArray> myArrays = new ArrayList<>();

    @Override
    public boolean addMyArray(MyArray myArray) {
        logger.debug("Attempting to add MyArray to repository: {}",
                myArray != null ? "id=" + myArray.getId() : "null");

        return myArrays.add(myArray);
    }

    @Override
    public boolean removeMyArray(MyArray myArray) {
        logger.debug("Attempting to remove MyArray from repository: {}",
                myArray != null ? "id=" + myArray.getId() : "null");

        return myArrays.remove(myArray);
    }

    @Override
    public List<MyArray> sort(Comparator<? super MyArray> c) {
        logger.debug("Starting sort operation with comparator: {}",
                c != null ? c.getClass().getSimpleName() : "null");

        if (c == null) {
            logger.warn("Cannot sort with null comparator");
            return new ArrayList<>(myArrays);
        }

        var myArraysCopy = new ArrayList<>(myArrays);
        myArraysCopy.sort(c);
        return myArraysCopy;
    }

    @Override
    public List<MyArray> query(MyArraySpecification specification) {
        logger.debug("Starting query operation with specification: {}",
                specification != null ? specification.getClass().getSimpleName() : "null");

        if (specification == null) {
            logger.warn("Cannot query with null specification, returning all arrays");
            return new ArrayList<>(myArrays);
        }

        return myArrays.stream().filter(specification::specify).toList();
    }
}
