package by.zgirskaya.course.service.operation.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.operation.CustomArrayOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class StreamCustomArrayOperationImpl implements CustomArrayOperation {

    private static final Logger logger = LogManager.getLogger(StreamCustomArrayOperationImpl.class);

    @Override
    public String findMinValue(CustomArray customArray) throws CustomArrayException {
        logger.debug("Finding min value using streams for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot find min value for null CustomArray using streams");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.error("Cannot find min value in empty CustomArray id: {} using streams", customArray.getId());
            throw new CustomArrayException("Cannot find min value in empty array");
        }

        try {
            logger.debug("Processing {} elements using stream min operation", customArray.getMyArray().length);

            Optional<String> minValue = IntStream.range(0, customArray.getMyArray().length)
                    .boxed()
                    .min(Comparator.comparingInt(customArray::calculateArrayElementAtIndexOf))
                    .map(i -> {
                        String element = customArray.getMyArray()[i];
                        int numericValue = customArray.calculateArrayElementAtIndexOf(i);
                        logger.trace("Candidate for min: element[{}] = '{}' (numeric: {})", i, element, numericValue);
                        return element;
                    });

            String result = minValue.orElseThrow(() -> {
                logger.error("No min value found in stream processing for CustomArray id: {}", customArray.getId());
                return new CustomArrayException("No min value found");
            });

            logger.info("Found min value using streams for CustomArray id {}: '{}'",
                    customArray.getId(), result);
            return result;

        } catch (CustomArrayException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error finding min value using streams for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to find min value using streams", e);
        }
    }

    @Override
    public String findMaxValue(CustomArray customArray) throws CustomArrayException {
        logger.debug("Finding max value using streams for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot find max value for null CustomArray using streams");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.error("Cannot find max value in empty CustomArray id: {} using streams", customArray.getId());
            throw new CustomArrayException("Cannot find max value in empty array");
        }

        try {
            logger.debug("Processing {} elements using stream max operation", customArray.getMyArray().length);

            Optional<String> maxValue = IntStream.range(0, customArray.getMyArray().length)
                    .boxed()
                    .max(Comparator.comparingInt(customArray::calculateArrayElementAtIndexOf))
                    .map(i -> {
                        String element = customArray.getMyArray()[i];
                        int numericValue = customArray.calculateArrayElementAtIndexOf(i);
                        logger.trace("Candidate for max: element[{}] = '{}' (numeric: {})", i, element, numericValue);
                        return element;
                    });

            String result = maxValue.orElseThrow(() -> {
                logger.error("No max value found in stream processing for CustomArray id: {}", customArray.getId());
                return new CustomArrayException("No max value found");
            });

            logger.info("Found max value using streams for CustomArray id {}: '{}'",
                    customArray.getId(), result);
            return result;

        } catch (CustomArrayException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error finding max value using streams for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to find max value using streams", e);
        }
    }

    @Override
    public CustomArray replaceCustomArrayElement(CustomArray customArray, String value, int index) throws CustomArrayException {
        logger.debug("Replacing element using streams in CustomArray id {} at index {} with value '{}'",
                customArray != null ? customArray.getId() : "null", index, value);

        if (customArray == null) {
            logger.error("Cannot replace element in null CustomArray using streams");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (value == null) {
            logger.error("Cannot replace with null value in CustomArray id: {} using streams", customArray.getId());
            throw new CustomArrayException("Replacement value cannot be null");
        }

        try {
            String[] srcArray = customArray.getMyArray();
            logger.debug("Source array size: {} for stream replacement", srcArray.length);

            if (index < 0 || index >= srcArray.length) {
                logger.error("Index {} out of bounds for array size {} in CustomArray id {} using streams",
                        index, srcArray.length, customArray.getId());
                throw new CustomArrayException("Index out of bounds: " + index +
                        ". Array size: " + srcArray.length);
            }

            logger.trace("Original element at index {}: '{}'", index, srcArray[index]);

            String[] newArray = IntStream.range(0, srcArray.length)
                    .mapToObj(i -> {
                        if (i == index) {
                            logger.trace("Replacing element at index {}: '{}' -> '{}'", i, srcArray[i], value);
                            return value;
                        } else {
                            return srcArray[i];
                        }
                    })
                    .toArray(String[]::new);

            CustomArray result = CustomArray.newBuilder()
                    .setMyArray(newArray)
                    .build();

            logger.info("Successfully replaced element using streams in CustomArray id {} at index {} with '{}'",
                    customArray.getId(), index, value);

            return result;

        } catch (CustomArrayException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error replacing element using streams in CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to replace array element using streams", e);
        }
    }

    @Override
    public double calculateAverageValue(CustomArray customArray) throws CustomArrayException {
        logger.debug("Calculating average value using streams for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot calculate average for null CustomArray using streams");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.error("Cannot calculate average for empty CustomArray id: {} using streams", customArray.getId());
            throw new CustomArrayException("Cannot calculate average for empty array");
        }

        try {
            logger.debug("Calculating average for {} elements using streams", customArray.getMyArray().length);

            double average = IntStream.range(0, customArray.getMyArray().length)
                    .mapToDouble(customArray::calculateArrayElementAtIndexOf)
                    .peek(value -> logger.trace("Stream element value: {}", value))
                    .average()
                    .orElseThrow(() -> {
                        logger.error("Cannot calculate average using streams for CustomArray id: {}", customArray.getId());
                        return new CustomArrayException("Cannot calculate average");
                    });

            logger.info("Calculated average using streams for CustomArray id {}: {:.2f}",
                    customArray.getId(), average);

            return average;

        } catch (CustomArrayException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error calculating average using streams for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to calculate average value using streams", e);
        }
    }

    @Override
    public int calculateSum(CustomArray customArray) throws CustomArrayException {
        logger.debug("Calculating sum using streams for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot calculate sum for null CustomArray using streams");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty CustomArray id {} provided, returning sum 0 using streams", customArray.getId());
            return 0;
        }

        try {
            logger.debug("Calculating sum for {} elements using streams", customArray.getMyArray().length);

            int sum = IntStream.range(0, customArray.getMyArray().length)
                    .map(customArray::calculateArrayElementAtIndexOf)
                    .peek(value -> logger.trace("Stream element value: {}", value))
                    .sum();

            logger.info("Calculated sum using streams for CustomArray id {}: {}",
                    customArray.getId(), sum);

            return sum;

        } catch (Exception e) {
            logger.error("Error calculating sum using streams for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to calculate sum using streams", e);
        }
    }

    @Override
    public int calculatePositiveValues(CustomArray customArray) throws CustomArrayException {
        logger.debug("Counting positive values using streams for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot count positive values for null CustomArray using streams");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty CustomArray id {} provided, returning positive count 0 using streams", customArray.getId());
            return 0;
        }

        try {
            logger.debug("Counting positive values in {} elements using streams", customArray.getMyArray().length);

            long positiveCount = IntStream.range(0, customArray.getMyArray().length)
                    .map(customArray::calculateArrayElementAtIndexOf)
                    .peek(value -> {
                        if (value > 0) {
                            logger.trace("Positive value found in stream: {}", value);
                        }
                    })
                    .filter(value -> value > 0)
                    .count();

            logger.info("Counted positive values using streams for CustomArray id {}: {} out of {} elements",
                    customArray.getId(), positiveCount, customArray.getMyArray().length);

            return (int) positiveCount;

        } catch (Exception e) {
            logger.error("Error counting positive values using streams for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to calculate positive values count using streams", e);
        }
    }

    @Override
    public int calculateNegativeValues(CustomArray customArray) throws CustomArrayException {
        logger.debug("Counting negative values using streams for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot count negative values for null CustomArray using streams");
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty CustomArray id {} provided, returning negative count 0 using streams", customArray.getId());
            return 0;
        }

        try {
            logger.debug("Counting negative values in {} elements using streams", customArray.getMyArray().length);

            long negativeCount = IntStream.range(0, customArray.getMyArray().length)
                    .map(customArray::calculateArrayElementAtIndexOf)
                    .peek(value -> {
                        if (value < 0) {
                            logger.trace("Negative value found in stream: {}", value);
                        }
                    })
                    .filter(value -> value < 0)
                    .count();

            logger.info("Counted negative values using streams for CustomArray id {}: {} out of {} elements",
                    customArray.getId(), negativeCount, customArray.getMyArray().length);

            return (int) negativeCount;

        } catch (Exception e) {
            logger.error("Error counting negative values using streams for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Failed to calculate negative values count using streams", e);
        }
    }
}