package main.by.java.course.service.stream;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.service.operation.MyArrayOperation;

public class StreamMyArrayOperationImpl implements MyArrayOperation {

    @Override
    public String findMinValue(MyArray myArray) throws MyArrayException {
        return "";
    }

    @Override
    public String findMaxValue(MyArray myArray) throws MyArrayException {
        return "";
    }

    @Override
    public MyArray replaceMyArrayElement(MyArray myArray, String value, int index) throws MyArrayException {
        return null;
    }

    @Override
    public double calculateAverageValue(MyArray myArray) throws MyArrayException {
        return 0;
    }

    @Override
    public int calculateSum(MyArray myArray) throws MyArrayException {
        return 0;
    }

    @Override
    public int calculatePositiveValues(MyArray myArray) throws MyArrayException {
        return 0;
    }

    @Override
    public int calculateNegativeValues(MyArray myArray) throws MyArrayException {
        return 0;
    }
}
