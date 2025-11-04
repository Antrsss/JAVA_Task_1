package by.zgirskaya.course.service.sort.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.sort.CustomArraySort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomArraySortImpl implements CustomArraySort {

    private static final Logger logger = LogManager.getLogger(CustomArraySortImpl.class);

    @Override
    public CustomArray selectionSort(CustomArray customArray) throws CustomArrayException {
        logger.debug("Starting selection sort for MyArray");

        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty array provided to selection sort, returning original");
            return customArray;
        }

        try {
            String[] array = customArray.getMyArray().clone();
            logger.debug("Cloned array with {} elements for selection sort", array.length);

            int swapCount = 0;
            for (int i = 0; i < array.length - 1; i++) {
                int minIndex = i;

                for (int j = i + 1; j < array.length; j++) {
                    if (array[j].compareTo(array[minIndex]) < 0) {
                        minIndex = j;
                    }
                }

                if (minIndex != i) {
                    String temp = array[i];
                    array[i] = array[minIndex];
                    array[minIndex] = temp;
                    swapCount++;
                    logger.trace("Swapped elements at indices {} and {}", i, minIndex);
                }
            }

            logger.debug("Selection sort completed with {} swaps", swapCount);
            return CustomArray.newBuilder()
                    .setMyArray(array)
                    .build();

        } catch (Exception e) {
            logger.error("Error during selection sort: {}", e.getMessage());
            throw new CustomArrayException("Selection sort failed", e);
        }
    }

    @Override
    public CustomArray mergeSort(CustomArray customArray) throws CustomArrayException {
        logger.debug("Starting merge sort for MyArray");

        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty array provided to merge sort, returning original");
            return customArray;
        }

        try {
            String[] array = customArray.getMyArray().clone();
            logger.debug("Cloned array with {} elements for merge sort", array.length);

            mergeSort(array, 0, array.length - 1);

            logger.debug("Merge sort completed successfully");
            return CustomArray.newBuilder()
                    .setMyArray(array)
                    .build();

        } catch (Exception e) {
            logger.error("Error during merge sort: {}", e.getMessage());
            throw new CustomArrayException("Merge sort failed", e);
        }
    }

    private void mergeSort(String[] array, int left, int right){
        if (left < right) {
            int mid = left + (right - left) / 2;
            logger.trace("Merge sort recursion: left={}, mid={}, right={}", left, mid, right);

            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private void merge(String[] array, int left, int mid, int right) {
        String[] leftArray = new String[mid - left + 1];
        String[] rightArray = new String[right - mid];

        System.arraycopy(array, left, leftArray, 0, leftArray.length);
        System.arraycopy(array, mid + 1, rightArray, 0, rightArray.length);

        int i = 0;
        int j = 0;
        int k = left;
        int mergeCount = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i].compareTo(rightArray[j]) <= 0) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
            mergeCount++;
        }

        while (i < leftArray.length) {
            array[k] = leftArray[i];
            i++;
            k++;
            mergeCount++;
        }

        while (j < rightArray.length) {
            array[k] = rightArray[j];
            j++;
            k++;
            mergeCount++;
        }

        logger.trace("Merged {} elements from indices {} to {}", mergeCount, left, right);
    }

    @Override
    public CustomArray quickSort(CustomArray customArray) throws CustomArrayException {
        logger.debug("Starting quick sort for MyArray");

        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            logger.warn("Empty array provided to quick sort, returning original");
            return customArray;
        }

        try {
            String[] array = customArray.getMyArray().clone();
            logger.debug("Cloned array with {} elements for quick sort", array.length);

            quickSort(array, 0, array.length - 1);

            logger.debug("Quick sort completed successfully");
            return CustomArray.newBuilder()
                    .setMyArray(array)
                    .build();

        } catch (Exception e) {
            logger.error("Error during quick sort: {}", e.getMessage());
            throw new CustomArrayException("Quick sort failed", e);
        }
    }

    private void quickSort(String[] array, int low, int high) throws CustomArrayException {
        if (low < high) {
            logger.trace("Quick sort partition: low={}, high={}", low, high);

            int pivotIndex = partition(array, low, high);
            logger.trace("Pivot element '{}' placed at index {}", array[pivotIndex], pivotIndex);

            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private int partition(String[] array, int low, int high) throws CustomArrayException {
        try {
            String pivot = array[high];
            int i = low - 1;
            int swapCount = 0;

            for (int j = low; j < high; j++) {
                if (array[j].compareTo(pivot) <= 0) {
                    i++;

                    if (i != j) {
                        String temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                        swapCount++;
                        logger.trace("Swapped elements at indices {} and {}", i, j);
                    }
                }
            }

            if (i + 1 != high) {
                String temp = array[i + 1];
                array[i + 1] = array[high];
                array[high] = temp;
                swapCount++;
                logger.trace("Placed pivot at final position: index {}", i + 1);
            }

            logger.trace("Partition completed with {} swaps, pivot index: {}", swapCount, i + 1);
            return i + 1;

        } catch (Exception e) {
            logger.error("Error during partition: {}", e.getMessage());
            throw new CustomArrayException("Partition failed in quick sort", e);
        }
    }
}