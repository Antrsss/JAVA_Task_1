package main.by.java.course.service.sort.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.service.sort.MyArraySort;

import java.util.Arrays;
import java.util.stream.IntStream;

public class StreamMyArraySortImpl implements MyArraySort {

    @Override
    public MyArray selectionSort(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null for sorting");
        }

        if (myArray.isEmpty()) {
            return myArray;
        }

        String[] array = myArray.getMyArray().clone();

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

        return MyArray.newBuilder()
                .setMyArray(array)
                .build();
    }

    @Override
    public MyArray mergeSort(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null for sorting");
        }

        if (myArray.isEmpty()) {
            return myArray;
        }

        String[] sortedArray = Arrays.stream(myArray.getMyArray())
                .sorted()
                .toArray(String[]::new);

        return MyArray.newBuilder()
                .setMyArray(sortedArray)
                .build();
    }

    @Override
    public MyArray quickSort(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null for sorting");
        }

        if (myArray.isEmpty()) {
            return myArray;
        }

        String[] sortedArray = quickSortRecursive(myArray.getMyArray());

        return MyArray.newBuilder()
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