package main.by.java.course.service.sort;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;

public interface MyArraySort {
    MyArray selectionSort(MyArray myArray) throws MyArrayException;
    MyArray mergeSort(MyArray myArray) throws MyArrayException;
    MyArray quickSort(MyArray myArray) throws MyArrayException;
}