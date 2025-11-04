package by.zgirskaya.course.warehouse;

import by.zgirskaya.course.entity.CustomArrayParameters;
import by.zgirskaya.course.exception.CustomArrayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CustomArrayWarehouse {
    private static final Logger logger = LogManager.getLogger();

    public static CustomArrayWarehouse instance;
    private final Map<Integer, CustomArrayParameters> myArrayParametersMap = new HashMap<>();

    private CustomArrayWarehouse() {}

    public static CustomArrayWarehouse getInstance() {
        if (instance == null) {
            logger.debug("Creating new MyArrayWarehouse instance");
            instance = new CustomArrayWarehouse();
        }

        logger.trace("Returning MyArrayWarehouse instance");
        return instance;
    }

    public Map<Integer, CustomArrayParameters> getCustomArrayParametersMap() {
        logger.trace("Returning copy with {} entries", myArrayParametersMap.size());
        return Map.copyOf(myArrayParametersMap);
    }

    public void putCustomArrayParametersMap(int id, CustomArrayParameters customArrayParameters) throws CustomArrayException {
        if (customArrayParameters == null) {
            logger.warn("Attempt to put null MyArrayParameters for id: {}", id);
            throw new CustomArrayException("Warehouse: myArrayParameter cannot be null");
        }

        myArrayParametersMap.put(id, customArrayParameters);
    }
}