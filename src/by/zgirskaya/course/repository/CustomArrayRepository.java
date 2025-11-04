package by.zgirskaya.course.repository;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.specification.CustomArraySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CustomArrayRepository {

    private static final Logger logger = LogManager.getLogger();

    public final List<CustomArray> customArrays = new ArrayList<>();

    public List<CustomArray> sort(Comparator<? super CustomArray> comparator) {
        logger.debug("Starting sort operation with comparator: {}",
                comparator != null ? comparator.getClass().getSimpleName() : "null");

        var myArraysCopy = new ArrayList<>(customArrays);
        myArraysCopy.sort(comparator);

        return myArraysCopy;
    }

    public List<CustomArray> query(CustomArraySpecification specification) {
        logger.debug("Starting query operation with specification: {}",
                specification != null ? specification.getClass().getSimpleName() : "null");

        return customArrays.stream().filter(specification::specify).toList();
    }
}
