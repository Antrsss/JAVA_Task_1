package main.by.java.course.service.operation;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;

public interface MyArrayOperation {
    String findMinValue(MyArray myArray) throws MyArrayException;
    String findMaxValue(MyArray myArray) throws MyArrayException;
    MyArray replaceMyArrayElement(MyArray myArray, String value, int index) throws MyArrayException;
    double calculateAverageValue(MyArray myArray) throws MyArrayException;
    int calculateSum(MyArray myArray) throws MyArrayException;
    int calculatePositiveValues(MyArray myArray) throws MyArrayException;
    int calculateNegativeValues(MyArray myArray) throws MyArrayException;
}