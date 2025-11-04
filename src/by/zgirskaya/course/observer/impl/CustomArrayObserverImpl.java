package by.zgirskaya.course.observer.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.entity.CustomArrayParameters;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.observer.CustomArrayObserver;
import by.zgirskaya.course.service.operation.impl.CustomArrayOperationImpl;
import by.zgirskaya.course.warehouse.CustomArrayWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomArrayObserverImpl implements CustomArrayObserver {
    private static final Logger logger = LogManager.getLogger();
    private final CustomArrayWarehouse warehouse;
    private final CustomArrayOperationImpl arrayOperations;

    public CustomArrayObserverImpl() {
        this.warehouse = CustomArrayWarehouse.getInstance();
        this.arrayOperations = new CustomArrayOperationImpl();
        logger.debug("MyArrayObserverImpl initialized");
    }

    @Override
    public void handleEvent(CustomArray customArray) {
        if (customArray == null) {
            logger.error("Cannot handle event for null MyArray");
            return;
        }

        try {
            CustomArrayParameters parameters = calculateParametersUsingOperations(customArray);
            warehouse.putCustomArrayParametersMap(customArray.getId(), parameters);

            logger.debug("Updated parameters for MyArray id {} in warehouse", customArray.getId());

        } catch (CustomArrayException e) {
            logger.error("Error for MyArray id {}: {}",
                    customArray.getId(), e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error handling event for MyArray id {}: {}",
                    customArray.getId(), e.getMessage());
        }
    }

    private CustomArrayParameters calculateParametersUsingOperations(CustomArray customArray)
            throws CustomArrayException {

        logger.debug("Calculating parameters for MyArray id: {}", customArray.getId());

        String minValue = arrayOperations.findMinValue(customArray);
        String maxValue = arrayOperations.findMaxValue(customArray);
        double averageValue = arrayOperations.calculateAverageValue(customArray);
        int arraySum = arrayOperations.calculateSum(customArray);
        int positiveValuesCount = arrayOperations.calculatePositiveValues(customArray);
        int negativeValuesCount = arrayOperations.calculateNegativeValues(customArray);

        return new CustomArrayParameters(
                customArray.getId(),
                minValue,
                maxValue,
                averageValue,
                arraySum,
                positiveValuesCount,
                negativeValuesCount
        );
    }
}
