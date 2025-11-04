package by.zgirskaya.course.warehouse;

import by.zgirskaya.course.entity.CustomArrayParameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CustomArrayWarehouse {
    private static final Logger logger = LogManager.getLogger();

    public static CustomArrayWarehouse instance;

    private final Map<Integer, CustomArrayParameters> customArrayParametersMap = new HashMap<>();

    private CustomArrayWarehouse() {}

    public static CustomArrayWarehouse getInstance() {
        if (instance == null) {
            logger.debug("Creating new MyArrayWarehouse instance");
            instance = new CustomArrayWarehouse();
        }

        return instance;
    }

    public CustomArrayParameters put(Integer id, CustomArrayParameters customArrayParameters) {
        if (customArrayParameters == null) {
            logger.warn("Attempt to put null MyArrayParameters for id: {}", id);
        }
        return customArrayParametersMap.put(id, customArrayParameters);
    }

    public Map<Integer, CustomArrayParameters> getMap() {
        logger.info("Returning copy with {} entries", customArrayParametersMap.size());
        return Map.copyOf(customArrayParametersMap);
    }
}