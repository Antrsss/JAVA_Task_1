package main.by.java.course.service.operation.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.service.operation.MyArrayOperation;

public class MyArrayOperationImpl implements MyArrayOperation {

    @Override
    public String findMinValue(MyArray myArray) {
        String[] arr = myArray.getMyArray();

        int minValue = calculateArrayElement(arr[0]);
        int minIndex = 0;
        int currentValue;

        for (int i = 1; i < arr.length; i++) {
            currentValue = calculateArrayElement(arr[i]);

            if (currentValue < minValue) {
                minValue = currentValue;
                minIndex = i;
            }
        }

        return arr[minIndex];
    }

    @Override
    public String findMaxValue(MyArray myArray) {
        String[] arr = myArray.getMyArray();

        int maxValue = calculateArrayElement(arr[0]);
        int maxIndex = 0;
        int currentValue;

        for (int i = 1; i < arr.length; i++) {
            currentValue = calculateArrayElement(arr[i]);

            if (currentValue > maxValue) {
                maxValue = currentValue;
                maxIndex = i;
            }
        }

        return arr[maxIndex];
    }

    @Override
    public MyArray replaceMyArrayElement(MyArray myArray, String value, int index) {
        return null;
    }

    @Override
    public double calculateAverageValue(MyArray myArray) {
        String[] arr = myArray.getMyArray();
        int currentSum = 0;

        for (int i = 1; i < arr.length; i++) {
            currentSum += calculateArrayElement(arr[i]);
        }

        return (double)currentSum / arr.length;
    }

    @Override
    public int calculateSum(MyArray myArray) {
        String[] arr = myArray.getMyArray();
        int currentSum = 0;

        for (int i = 1; i < arr.length; i++) {
            currentSum += calculateArrayElement(arr[i]);
        }

        return currentSum;
    }

    @Override
    public int calculatePositiveValues(MyArray myArray) {
        String[] arr = myArray.getMyArray();
        int positiveCount = 0;

        for (int i = 1; i < arr.length; i++) {
            if (calculateArrayElement(arr[i]) > 0) {
                positiveCount += 1;
            }
        }

        return positiveCount;
    }

    @Override
    public int calculateNegativeValues(MyArray myArray) {
        String[] arr = myArray.getMyArray();
        int negativeCount = 0;

        for (int i = 1; i < arr.length; i++) {
            if (calculateArrayElement(arr[i]) < 0) {
                negativeCount += 1;
            }
        }

        return negativeCount;
    }

    private int calculateArrayElement(String str) {
        int value = 0;
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c < 'Z') {
                value += c;
            } else if (c > 'a') {
                value -= c;
            }
        }

        return value;
    }
}
