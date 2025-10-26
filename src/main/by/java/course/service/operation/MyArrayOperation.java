package main.by.java.course.service.operation;

import main.by.java.course.entity.MyArray;

public interface MyArrayOperation {
    String findMinValue(MyArray myArray);
    String findMaxValue(MyArray myArray);
    MyArray replaceMyArrayElement(MyArray myArray, String value, int index);
    double calculateAverageValue(MyArray myArray);
    int calculateSum(MyArray myArray);
    int calculatePositiveValues(MyArray myArray);
    int calculateNegativeValues(MyArray myArray);
}
