package main.by.java.course.service.operation.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.service.operation.MyArrayOperation;

public class MyArrayOperationImpl implements MyArrayOperation {

    @Override
    public String findMinValue(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            throw new MyArrayException("Cannot find min value in empty array");
        }

        String[] arr = myArray.getMyArray();
        int minValue = myArray.calculateArrayElementAtIndexOf(0);
        int minIndex = 0;
        int currentValue;

        for (int i = 1; i < arr.length; i++) {
            currentValue = myArray.calculateArrayElementAtIndexOf(i);

            if (currentValue < minValue) {
                minValue = currentValue;
                minIndex = i;
            }
        }

        return arr[minIndex];
    }

    @Override
    public String findMaxValue(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            throw new MyArrayException("Cannot find max value in empty array");
        }

        String[] arr = myArray.getMyArray();
        int maxValue = myArray.calculateArrayElementAtIndexOf(0);
        int maxIndex = 0;
        int currentValue;

        for (int i = 1; i < arr.length; i++) {
            currentValue = myArray.calculateArrayElementAtIndexOf(i);

            if (currentValue > maxValue) {
                maxValue = currentValue;
                maxIndex = i;
            }
        }

        return arr[maxIndex];
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
            throw new MyArrayException("Index out of bounds: " + index +
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

        String[] arr = myArray.getMyArray();
        int currentSum = 0;

        for (int i = 0; i < arr.length; i++) {
            currentSum += myArray.calculateArrayElementAtIndexOf(i);
        }

        return (double) currentSum / arr.length;
    }

    @Override
    public int calculateSum(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            return 0;
        }

        String[] arr = myArray.getMyArray();
        int currentSum = 0;

        for (int i = 0; i < arr.length; i++) {
            currentSum += myArray.calculateArrayElementAtIndexOf(i);
        }

        return currentSum;
    }

    @Override
    public int calculatePositiveValues(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            return 0;
        }

        String[] arr = myArray.getMyArray();
        int positiveCount = 0;

        for (int i = 0; i < arr.length; i++) {
            if (myArray.calculateArrayElementAtIndexOf(i) > 0) {
                positiveCount += 1;
            }
        }

        return positiveCount;
    }

    @Override
    public int calculateNegativeValues(MyArray myArray) throws MyArrayException {
        if (myArray == null) {
            throw new MyArrayException("MyArray cannot be null");
        }

        if (myArray.isEmpty()) {
            return 0;
        }

        String[] arr = myArray.getMyArray();
        int negativeCount = 0;

        for (int i = 0; i < arr.length; i++) {
            if (myArray.calculateArrayElementAtIndexOf(i) < 0) {
                negativeCount += 1;
            }
        }

        return negativeCount;
    }
}