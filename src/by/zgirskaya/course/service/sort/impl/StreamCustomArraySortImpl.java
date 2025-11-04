package by.zgirskaya.course.service.sort.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.sort.CustomArraySort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.IntStream;

public class StreamCustomArraySortImpl implements CustomArraySort {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public CustomArray selectionSort(CustomArray customArray) throws CustomArrayException {
        logger.debug("Starting stream selection sort for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot perform selection sort on null CustomArray");
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty array provided to stream selection sort, returning original");
            return customArray;
        }

        try {
            String[] array = customArray.getMyArray().clone();
            logger.debug("Cloned array with {} elements for stream selection sort", array.length);
            logger.trace("Original array: {}", Arrays.toString(array));

            int swapCount = 0;
            IntStream.range(0, array.length - 1)
                    .forEach(i -> {
                        int minIndex = IntStream.range(i, array.length)
                                .reduce((a, b) -> array[a].compareTo(array[b]) < 0 ? a : b)
                                .orElse(i);

                        if (minIndex != i) {
                            String temp = array[i];
                            array[i] = array[minIndex];
                            array[minIndex] = temp;
                            logger.trace("Swapped elements at indices {} and {}: '{}' <-> '{}'",
                                    i, minIndex, temp, array[i]);
                        }
                    });

            logger.debug("Stream selection sort completed for CustomArray id: {}", customArray.getId());
            logger.trace("Sorted array: {}", Arrays.toString(array));

            return CustomArray.newBuilder()
                    .setMyArray(array)
                    .build();

        } catch (Exception e) {
            logger.error("Error during stream selection sort for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Stream selection sort failed", e);
        }
    }

    @Override
    public CustomArray mergeSort(CustomArray customArray) throws CustomArrayException {
        logger.debug("Starting stream merge sort for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot perform merge sort on null CustomArray");
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty array provided to stream merge sort, returning original");
            return customArray;
        }

        try {
            logger.debug("Processing array with {} elements using stream merge sort",
                    customArray.getMyArray().length);
            logger.trace("Original array: {}", Arrays.toString(customArray.getMyArray()));

            String[] sortedArray = Arrays.stream(customArray.getMyArray())
                    .sorted()
                    .toArray(String[]::new);

            logger.debug("Stream merge sort completed successfully for CustomArray id: {}",
                    customArray.getId());
            logger.trace("Sorted array: {}", Arrays.toString(sortedArray));

            return CustomArray.newBuilder()
                    .setMyArray(sortedArray)
                    .build();

        } catch (Exception e) {
            logger.error("Error during stream merge sort for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Stream merge sort failed", e);
        }
    }

    @Override
    public CustomArray quickSort(CustomArray customArray) throws CustomArrayException {
        logger.debug("Starting stream quick sort for CustomArray id: {}",
                customArray != null ? customArray.getId() : "null");

        if (customArray == null) {
            logger.error("Cannot perform quick sort on null CustomArray");
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty array provided to stream quick sort, returning original");
            return customArray;
        }

        try {
            logger.debug("Processing array with {} elements using stream quick sort",
                    customArray.getMyArray().length);
            logger.trace("Original array: {}", Arrays.toString(customArray.getMyArray()));

            String[] sortedArray = quickSortRecursive(customArray.getMyArray(), 0);

            logger.debug("Stream quick sort completed successfully for CustomArray id: {}",
                    customArray.getId());
            logger.trace("Sorted array: {}", Arrays.toString(sortedArray));

            return CustomArray.newBuilder()
                    .setMyArray(sortedArray)
                    .build();

        } catch (Exception e) {
            logger.error("Error during stream quick sort for CustomArray id {}: {}",
                    customArray.getId(), e.getMessage());
            throw new CustomArrayException("Stream quick sort failed", e);
        }
    }

    private String[] quickSortRecursive(String[] array, int recursionLevel) {
        logger.trace("Quick sort recursion level {} for array size: {}", recursionLevel, array.length);

        if (array.length <= 1) {
            logger.trace("Base case reached for array size: {}", array.length);
            return array;
        }

        String pivot = array[array.length / 2];
        logger.trace("Selected pivot: '{}' for array size: {}", pivot, array.length);

        String[] less = Arrays.stream(array)
                .filter(element -> element.compareTo(pivot) < 0)
                .toArray(String[]::new);
        logger.trace("Less partition size: {} (elements < '{}')", less.length, pivot);

        String[] equal = Arrays.stream(array)
                .filter(element -> element.compareTo(pivot) == 0)
                .toArray(String[]::new);
        logger.trace("Equal partition size: {} (elements == '{}')", equal.length, pivot);

        String[] greater = Arrays.stream(array)
                .filter(element -> element.compareTo(pivot) > 0)
                .toArray(String[]::new);
        logger.trace("Greater partition size: {} (elements > '{}')", greater.length, pivot);

        String[] result = concatenateArrays(
                quickSortRecursive(less, recursionLevel + 1),
                equal,
                quickSortRecursive(greater, recursionLevel + 1)
        );

        logger.trace("Merged partitions at recursion level {}, total elements: {}",
                recursionLevel, result.length);
        return result;
    }

    private String[] concatenateArrays(String[]... arrays) {
        logger.trace("Concatenating {} arrays", arrays.length);

        String[] result = Arrays.stream(arrays)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);

        logger.trace("Concatenated result size: {}", result.length);
        return result;
    }
}