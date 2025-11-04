package by.zgirskaya.course.service.operation.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.operation.CustomArrayOperation;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;

public class StreamCustomArrayOperationImpl implements CustomArrayOperation {

    @Override
    public String findMinValue(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            throw new CustomArrayException("Cannot find min value in empty array");
        }

        Optional<String> minValue = IntStream.range(0, customArray.getMyArray().length)
                .boxed()
                .min(Comparator.comparingInt(customArray::calculateArrayElementAtIndexOf))
                .map(i -> customArray.getMyArray()[i]);

        return minValue.orElseThrow(() -> new CustomArrayException("No min value found"));
    }

    @Override
    public String findMaxValue(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            throw new CustomArrayException("Cannot find max value in empty array");
        }

        Optional<String> maxValue = IntStream.range(0, customArray.getMyArray().length)
                .boxed()
                .max(Comparator.comparingInt(customArray::calculateArrayElementAtIndexOf))
                .map(i -> customArray.getMyArray()[i]);

        return maxValue.orElseThrow(() -> new CustomArrayException("No max value found"));
    }

    @Override
    public CustomArray replaceCustomArrayElement(CustomArray customArray, String value, int index) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (value == null) {
            throw new CustomArrayException("Replacement value cannot be null");
        }

        String[] srcArray = customArray.getMyArray();

        if (index < 0 || index >= srcArray.length) {
            throw new CustomArrayException("Index out of bounds: " + index + ". Array size: " + srcArray.length);
        }

        String[] newArray = IntStream.range(0, srcArray.length)
                .mapToObj(i -> i == index ? value : srcArray[i])
                .toArray(String[]::new);

        return CustomArray.newBuilder()
                .setMyArray(newArray)
                .build();
    }

    @Override
    public double calculateAverageValue(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            throw new CustomArrayException("Cannot calculate average for empty array");
        }

        return IntStream.range(0, customArray.getMyArray().length)
                .mapToDouble(customArray::calculateArrayElementAtIndexOf)
                .average()
                .orElseThrow(() -> new CustomArrayException("Cannot calculate average"));
    }

    @Override
    public int calculateSum(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            return 0;
        }

        return IntStream.range(0, customArray.getMyArray().length)
                .map(customArray::calculateArrayElementAtIndexOf)
                .sum();
    }

    @Override
    public int calculatePositiveValues(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            return 0;
        }

        return (int) IntStream.range(0, customArray.getMyArray().length)
                .map(customArray::calculateArrayElementAtIndexOf)
                .filter(value -> value > 0)
                .count();
    }

    @Override
    public int calculateNegativeValues(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            return 0;
        }

        return (int) IntStream.range(0, customArray.getMyArray().length)
                .map(customArray::calculateArrayElementAtIndexOf)
                .filter(value -> value < 0)
                .count();
    }
}