package main.by.java.course.observer.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.entity.MyArrayParameters;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.observer.MyArrayObserver;
import main.by.java.course.service.operation.impl.MyArrayOperationImpl;
import main.by.java.course.warehouse.MyArrayWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyArrayObserverImpl implements MyArrayObserver {
    private final Logger logger = LogManager.getLogger();
    private final MyArrayWarehouse warehouse;
    private final MyArrayOperationImpl arrayOperations;

    public MyArrayObserverImpl() {
        this.warehouse = MyArrayWarehouse.getInstance();
        this.arrayOperations = new MyArrayOperationImpl();
        logger.debug("MyArrayObserverImpl initialized");
    }

    @Override
    public void handleEvent(MyArray myArray) {
        if (myArray == null) {
            logger.error("Cannot handle event for null MyArray");
            return;
        }

        try {
            MyArrayParameters parameters = calculateParametersUsingOperations(myArray);
            warehouse.putMyArrayParametersMap(myArray.getId(), parameters);

            logger.debug("Updated parameters for MyArray id {} in warehouse", myArray.getId());

        } catch (MyArrayException e) {
            logger.error("Error for MyArray id {}: {}",
                    myArray.getId(), e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error handling event for MyArray id {}: {}",
                    myArray.getId(), e.getMessage());
        }
    }

    private MyArrayParameters calculateParametersUsingOperations(MyArray myArray)
            throws MyArrayException {

        logger.debug("Calculating parameters for MyArray id: {}", myArray.getId());

        String minValue = arrayOperations.findMinValue(myArray);
        String maxValue = arrayOperations.findMaxValue(myArray);
        double averageValue = arrayOperations.calculateAverageValue(myArray);
        int arraySum = arrayOperations.calculateSum(myArray);
        int positiveValuesCount = arrayOperations.calculatePositiveValues(myArray);
        int negativeValuesCount = arrayOperations.calculateNegativeValues(myArray);

        return new MyArrayParameters(
                myArray.getId(),
                minValue,
                maxValue,
                averageValue,
                arraySum,
                positiveValuesCount,
                negativeValuesCount
        );
    }
}
