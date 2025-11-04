package by.zgirskaya.course.service.sort.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.sort.CustomArraySort;

import java.util.Arrays;
import java.util.stream.IntStream;

public class StreamCustomArraySortImpl implements CustomArraySort {

    @Override
    public CustomArray selectionSort(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            return customArray;
        }

        String[] array = customArray.getMyArray().clone();

        IntStream.range(0, array.length - 1)
                .forEach(i -> {
                    int minIndex = IntStream.range(i, array.length)
                            .reduce((a, b) -> array[a].compareTo(array[b]) < 0 ? a : b)
                            .orElse(i);

                    if (minIndex != i) {
                        String temp = array[i];
                        array[i] = array[minIndex];
                        array[minIndex] = temp;
                    }
                });

        return CustomArray.newBuilder()
                .setMyArray(array)
                .build();
    }

    @Override
    public CustomArray mergeSort(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            return customArray;
        }

        String[] sortedArray = Arrays.stream(customArray.getMyArray())
                .sorted()
                .toArray(String[]::new);

        return CustomArray.newBuilder()
                .setMyArray(sortedArray)
                .build();
    }

    @Override
    public CustomArray quickSort(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null for sorting");
        }

        if (customArray.isEmpty()) {
            return customArray;
        }

        String[] sortedArray = quickSortRecursive(customArray.getMyArray());

        return CustomArray.newBuilder()
                .setMyArray(sortedArray)
                .build();
    }

    private String[] quickSortRecursive(String[] array) {
        if (array.length <= 1) {
            return array;
        }

        String pivot = array[array.length / 2];

        String[] less = Arrays.stream(array)
                .filter(element -> element.compareTo(pivot) < 0)
                .toArray(String[]::new);

        String[] equal = Arrays.stream(array)
                .filter(element -> element.compareTo(pivot) == 0)
                .toArray(String[]::new);

        String[] greater = Arrays.stream(array)
                .filter(element -> element.compareTo(pivot) > 0)
                .toArray(String[]::new);

        return concatenateArrays(
                quickSortRecursive(less),
                equal,
                quickSortRecursive(greater)
        );
    }

    private String[] concatenateArrays(String[]... arrays) {
        return Arrays.stream(arrays)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }
}