package main.by.java.course.warehouse;

import main.by.java.course.entity.MyArrayParameters;
import main.by.java.course.exception.MyArrayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MyArrayWarehouse {
    private static final Logger logger = LogManager.getLogger();

    public static MyArrayWarehouse instance;
    private final Map<Integer, MyArrayParameters> myArrayParametersMap = new HashMap<>();

    private MyArrayWarehouse() {}

    public static MyArrayWarehouse getInstance() {
        if (instance == null) {
            logger.debug("Creating new MyArrayWarehouse instance");
            instance = new MyArrayWarehouse();
        }

        logger.trace("Returning MyArrayWarehouse instance");
        return instance;
    }

    public Map<Integer, MyArrayParameters> getMyArrayParametersMap() {
        logger.trace("Returning copy with {} entries", myArrayParametersMap.size());
        return Map.copyOf(myArrayParametersMap);
    }

    public void putMyArrayParametersMap(int id, MyArrayParameters myArrayParameters) throws MyArrayException {
        if (myArrayParameters == null) {
            logger.warn("Attempt to put null MyArrayParameters for id: {}", id);
            throw new MyArrayException("Warehouse: myArrayParameter cannot be null");
        }

        myArrayParametersMap.put(id, myArrayParameters);
    }
}