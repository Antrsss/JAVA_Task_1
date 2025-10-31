package main.by.java.course.warehouse;

import main.by.java.course.entity.MyArrayParameters;
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
            instance = new MyArrayWarehouse();
        }
        return instance;
    }

    public Map<Integer, MyArrayParameters> getMyArrayParametersMap() {
        return Map.copyOf(myArrayParametersMap);
    }

    public void putMyArrayParametersMap(int id, MyArrayParameters myArrayParameters) {
        myArrayParametersMap.put(id, myArrayParameters);
    }
}
