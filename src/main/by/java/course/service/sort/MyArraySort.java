package main.by.java.course.service.sort;

import main.by.java.course.entity.MyArray;

public interface MyArraySort {
    MyArray selectionSort(MyArray myArray);
    MyArray mergeSort(MyArray myArray);
    MyArray quickSort(MyArray myArray);
}
