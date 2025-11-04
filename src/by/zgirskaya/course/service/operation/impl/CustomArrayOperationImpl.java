package by.zgirskaya.course.service.operation.impl;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.operation.CustomArrayOperation;

public class CustomArrayOperationImpl implements CustomArrayOperation {

    @Override
    public String findMinValue(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            throw new CustomArrayException("Cannot find min value in empty array");
        }

        String[] arr = customArray.getMyArray();
        int minValue = customArray.calculateArrayElementAtIndexOf(0);
        int minIndex = 0;
        int currentValue;

        for (int i = 1; i < arr.length; i++) {
            currentValue = customArray.calculateArrayElementAtIndexOf(i);

            if (currentValue < minValue) {
                minValue = currentValue;
                minIndex = i;
            }
        }

        return arr[minIndex];
    }

    @Override
    public String findMaxValue(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            throw new CustomArrayException("Cannot find max value in empty array");
        }

        String[] arr = customArray.getMyArray();
        int maxValue = customArray.calculateArrayElementAtIndexOf(0);
        int maxIndex = 0;
        int currentValue;

        for (int i = 1; i < arr.length; i++) {
            currentValue = customArray.calculateArrayElementAtIndexOf(i);

            if (currentValue > maxValue) {
                maxValue = currentValue;
                maxIndex = i;
            }
        }

        return arr[maxIndex];
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
            throw new CustomArrayException("Index out of bounds: " + index +
                    ". Array size: " + srcArray.length);
        }

        String[] newArray = new String[srcArray.length];
        for (int i = 0; i < srcArray.length; i++) {
            if (i == index) {
                newArray[i] = value;
            } else {
                newArray[i] = srcArray[i];
            }
        }

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

        String[] arr = customArray.getMyArray();
        int currentSum = 0;

        for (int i = 0; i < arr.length; i++) {
            currentSum += customArray.calculateArrayElementAtIndexOf(i);
        }

        return (double) currentSum / arr.length;
    }

    @Override
    public int calculateSum(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            return 0;
        }

        String[] arr = customArray.getMyArray();
        int currentSum = 0;

        for (int i = 0; i < arr.length; i++) {
            currentSum += customArray.calculateArrayElementAtIndexOf(i);
        }

        return currentSum;
    }

    @Override
    public int calculatePositiveValues(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            return 0;
        }

        String[] arr = customArray.getMyArray();
        int positiveCount = 0;

        for (int i = 0; i < arr.length; i++) {
            if (customArray.calculateArrayElementAtIndexOf(i) > 0) {
                positiveCount += 1;
            }
        }

        return positiveCount;
    }

    @Override
    public int calculateNegativeValues(CustomArray customArray) throws CustomArrayException {
        if (customArray == null) {
            throw new CustomArrayException("MyArray cannot be null");
        }

        if (customArray.isEmpty()) {
            return 0;
        }

        String[] arr = customArray.getMyArray();
        int negativeCount = 0;

        for (int i = 0; i < arr.length; i++) {
            if (customArray.calculateArrayElementAtIndexOf(i) < 0) {
                negativeCount += 1;
            }
        }

        return negativeCount;
    }
}