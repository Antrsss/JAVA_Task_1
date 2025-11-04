package by.zgirskaya.course.service.operation.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.operation.CustomArrayOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomArrayOperationImpl implements CustomArrayOperation {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String findMinValue(CustomArray customArray) throws CustomArrayException {
        logger.debug("Finding min value for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot find min value for null CustomArray");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.error("Cannot find min value in empty CustomArray id: {}", customArray.getId());
            throw new CustomArrayException("Cannot find min value in empty array");
        }

        try {
            String[] arr = customArray.getMyArray();
            logger.debug("Processing array with {} elements for min value", arr.length);

            int minValue = customArray.calculateArrayElementAtIndexOf(0);
            int minIndex = 0;
            int currentValue;

            logger.trace("Initial min value: {} at index 0 (element: '{}')", minValue, arr[0]);

            for (int i = 1; i < arr.length; i++) {
                currentValue = customArray.calculateArrayElementAtIndexOf(i);
                logger.trace("Element [{}]: '{}' = {}", i, arr[i], currentValue);

                if (currentValue < minValue) {
                    logger.trace("New min found: {} (previous: {})", currentValue, minValue);
                    minValue = currentValue;
                    minIndex = i;
                }
            }

            String result = arr[minIndex];
            logger.info("Found min value for CustomArray id {}: '{}' (numeric value: {}) at index {}",
                    customArray.getId(), result, minValue, minIndex);

            return result;

        } catch (Exception e) {
            logger.error("Error finding min value for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to find min value", e);
        }
    }

    @Override
    public String findMaxValue(CustomArray customArray) throws CustomArrayException {
        logger.debug("Finding max value for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot find max value for null CustomArray");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.error("Cannot find max value in empty CustomArray id: {}", customArray.getId());
            throw new CustomArrayException("Cannot find max value in empty array");
        }

        try {
            String[] arr = customArray.getMyArray();
            logger.debug("Processing array with {} elements for max value", arr.length);

            int maxValue = customArray.calculateArrayElementAtIndexOf(0);
            int maxIndex = 0;
            int currentValue;

            logger.trace("Initial max value: {} at index 0 (element: '{}')", maxValue, arr[0]);

            for (int i = 1; i < arr.length; i++) {
                currentValue = customArray.calculateArrayElementAtIndexOf(i);
                logger.trace("Element [{}]: '{}' = {}", i, arr[i], currentValue);

                if (currentValue > maxValue) {
                    logger.trace("New max found: {} (previous: {})", currentValue, maxValue);
                    maxValue = currentValue;
                    maxIndex = i;
                }
            }

            String result = arr[maxIndex];
            logger.info("Found max value for CustomArray id {}: '{}' (numeric value: {}) at index {}",
                    customArray.getId(), result, maxValue, maxIndex);

            return result;

        } catch (Exception e) {
            logger.error("Error finding max value for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to find max value", e);
        }
    }

    @Override
    public CustomArray replaceCustomArrayElement(CustomArray customArray, String value, int index) throws CustomArrayException {
        logger.debug("Replacing element in CustomArray id {} at index {} with value '{}'",
                customArray != null ? customArray.getId() : "null", index, value);

        if (customArray == null) {
            logger.error("Cannot replace element in null CustomArray");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (value == null) {
            logger.error("Cannot replace with null value in CustomArray id: {}", customArray.getId());
            throw new CustomArrayException("Replacement value cannot be null");
        }

        try {
            String[] srcArray = customArray.getMyArray();
            logger.debug("Source array size: {}", srcArray.length);

            if (index < 0 || index >= srcArray.length) {
                logger.error("Index {} out of bounds for array size {} in CustomArray id {}",
                        index, srcArray.length, customArray.getId());
                throw new CustomArrayException("Index out of bounds: " + index +
                        ". Array size: " + srcArray.length);
            }

            logger.trace("Original element at index {}: '{}'", index, srcArray[index]);

            String[] newArray = new String[srcArray.length];
            for (int i = 0; i < srcArray.length; i++) {
                if (i == index) {
                    newArray[i] = value;
                    logger.trace("Replaced element at index {}: '{}' -> '{}'", i, srcArray[i], value);
                } else {
                    newArray[i] = srcArray[i];
                }
            }

            CustomArray result = CustomArray.newBuilder()
                    .setMyArray(newArray)
                    .build();

            logger.info("Successfully replaced element in CustomArray id {} at index {} with '{}'",
                    customArray.getId(), index, value);

            return result;

        } catch (CustomArrayException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error replacing element in CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to replace array element", e);
        }
    }

    @Override
    public double calculateAverageValue(CustomArray customArray) throws CustomArrayException {
        logger.debug("Calculating average value for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot calculate average for null CustomArray");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.error("Cannot calculate average for empty CustomArray id: {}", customArray.getId());
            throw new CustomArrayException("Cannot calculate average for empty array");
        }

        try {
            String[] arr = customArray.getMyArray();
            logger.debug("Calculating average for {} elements", arr.length);

            int currentSum = 0;

            for (int i = 0; i < arr.length; i++) {
                int elementValue = customArray.calculateArrayElementAtIndexOf(i);
                currentSum += elementValue;
                logger.trace("Element [{}]: '{}' = {}, running sum: {}",
                        i, arr[i], elementValue, currentSum);
            }

            double average = (double) currentSum / arr.length;
            logger.info("Calculated average for CustomArray id {}: {:.2f} (sum: {}, elements: {})",
                    customArray.getId(), average, currentSum, arr.length);

            return average;

        } catch (Exception e) {
            logger.error("Error calculating average for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to calculate average value", e);
        }
    }

    @Override
    public int calculateSum(CustomArray customArray) throws CustomArrayException {
        logger.debug("Calculating sum for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot calculate sum for null CustomArray");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty CustomArray id {} provided, returning sum 0", customArray.getId());
            return 0;
        }

        try {
            String[] arr = customArray.getMyArray();
            logger.debug("Calculating sum for {} elements", arr.length);

            int currentSum = 0;

            for (int i = 0; i < arr.length; i++) {
                int elementValue = customArray.calculateArrayElementAtIndexOf(i);
                currentSum += elementValue;
                logger.trace("Element [{}]: '{}' = {}, running sum: {}",
                        i, arr[i], elementValue, currentSum);
            }

            logger.info("Calculated sum for CustomArray id {}: {} (elements: {})",
                    customArray.getId(), currentSum, arr.length);

            return currentSum;

        } catch (Exception e) {
            logger.error("Error calculating sum for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to calculate sum", e);
        }
    }

    @Override
    public int calculatePositiveValues(CustomArray customArray) throws CustomArrayException {
        logger.debug("Counting positive values for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot count positive values for null CustomArray");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty CustomArray id {} provided, returning positive count 0", customArray.getId());
            return 0;
        }

        try {
            String[] arr = customArray.getMyArray();
            logger.debug("Counting positive values in {} elements", arr.length);

            int positiveCount = 0;

            for (int i = 0; i < arr.length; i++) {
                int elementValue = customArray.calculateArrayElementAtIndexOf(i);
                boolean isPositive = elementValue > 0;

                if (isPositive) {
                    positiveCount++;
                    logger.trace("Positive element [{}]: '{}' = {}", i, arr[i], elementValue);
                } else {
                    logger.trace("Non-positive element [{}]: '{}' = {}", i, arr[i], elementValue);
                }
            }

            logger.info("Counted positive values for CustomArray id {}: {} out of {} elements",
                    customArray.getId(), positiveCount, arr.length);

            return positiveCount;

        } catch (Exception e) {
            logger.error("Error counting positive values for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to calculate positive values count", e);
        }
    }

    @Override
    public int calculateNegativeValues(CustomArray customArray) throws CustomArrayException {
        logger.debug("Counting negative values for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot count negative values for null CustomArray");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty CustomArray id {} provided, returning negative count 0", customArray.getId());
            return 0;
        }

        try {
            String[] arr = customArray.getMyArray();
            logger.debug("Counting negative values in {} elements", arr.length);

            int negativeCount = 0;

            for (int i = 0; i < arr.length; i++) {
                int elementValue = customArray.calculateArrayElementAtIndexOf(i);
                boolean isNegative = elementValue < 0;

                if (isNegative) {
                    negativeCount++;
                    logger.trace("Negative element [{}]: '{}' = {}", i, arr[i], elementValue);
                } else {
                    logger.trace("Non-negative element [{}]: '{}' = {}", i, arr[i], elementValue);
                }
            }

            logger.info("Counted negative values for CustomArray id {}: {} out of {} elements",
                    customArray.getId(), negativeCount, arr.length);

            return negativeCount;

        } catch (Exception e) {
            logger.error("Error counting negative values for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to calculate negative values count", e);
        }
    }
}