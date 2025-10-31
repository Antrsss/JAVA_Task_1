package main.by.java.course.specification.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.specification.MyArraySpecification;

public record IntervalSpecification(int minValue, int maxValue) implements MyArraySpecification {

    @Override
    public boolean specify(MyArray myArray) {
        String[] array = myArray.getMyArray();
        int currentValue;

        for (var string : array) {
            currentValue = calculateArrayElement(string);

            if (currentValue < minValue || currentValue > maxValue) {
                return false;
            }
        }
        return true;
    }

    private int calculateArrayElement(String str) {
        int value = 0;
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                value += c;
            } else if (c >= 'a' && c <= 'z') {
                value -= c;
            }
        }

        return value;
    }
}
