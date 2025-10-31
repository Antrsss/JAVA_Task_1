package main.by.java.course.service.operation.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.service.operation.MyArrayOperation;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class StreamMyArrayOperationImpl implements MyArrayOperation {

    @Override
    public String findMinValue(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            throw new MyArrayException("Cannot find min value in empty array");
        }

        Optional<String> minValue = IntStream.range(0, myArray.getMyArray().length)
                .boxed()
                .min(Comparator.comparingInt(myArray::calculateArrayElementAtIndexOf))
                .map(i -> myArray.getMyArray()[i]);

        return minValue.orElseThrow(() -> new MyArrayException("No min value found"));
    }

    @Override
    public String findMaxValue(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            throw new MyArrayException("Cannot find max value in empty array");
        }

        Optional<String> maxValue = IntStream.range(0, myArray.getMyArray().length)
                .boxed()
                .max(Comparator.comparingInt(myArray::calculateArrayElementAtIndexOf))
                .map(i -> myArray.getMyArray()[i]);

        return maxValue.orElseThrow(() -> new MyArrayException("No max value found"));
    }

    @Override
    public MyArray replaceMyArrayElement(MyArray myArray, String value, int index) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (value == null) {
            throw new MyArrayException("Replacement value cannot be null");
        }

        String[] srcArray = myArray.getMyArray();

        if (index < 0 || index >= srcArray.length) {
            throw new MyArrayException("Index out of bounds: " + index + ". Array size: " + srcArray.length);
        }

        String[] newArray = IntStream.range(0, srcArray.length)
                .mapToObj(i -> i == index ? value : srcArray[i])
                .toArray(String[]::new);

        return MyArray.newBuilder()
                .setMyArray(newArray)
                .build();
    }

    @Override
    public double calculateAverageValue(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            throw new MyArrayException("Cannot calculate average for empty array");
        }

        return IntStream.range(0, myArray.getMyArray().length)
                .mapToDouble(myArray::calculateArrayElementAtIndexOf)
                .average()
                .orElseThrow(() -> new MyArrayException("Cannot calculate average"));
    }

    @Override
    public int calculateSum(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            return 0;
        }

        return IntStream.range(0, myArray.getMyArray().length)
                .map(myArray::calculateArrayElementAtIndexOf)
                .sum();
    }

    @Override
    public int calculatePositiveValues(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            return 0;
        }

        return (int) IntStream.range(0, myArray.getMyArray().length)
                .map(myArray::calculateArrayElementAtIndexOf)
                .filter(value -> value > 0)
                .count();
    }

    @Override
    public int calculateNegativeValues(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            return 0;
        }

        return (int) IntStream.range(0, myArray.getMyArray().length)
                .map(myArray::calculateArrayElementAtIndexOf)
                .filter(value -> value < 0)
                .count();
    }
}