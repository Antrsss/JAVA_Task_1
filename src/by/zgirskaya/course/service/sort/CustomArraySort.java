package by.zgirskaya.course.service.sort;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;

public interface CustomArraySort {
    CustomArray selectionSort(CustomArray customArray) throws CustomArrayException;
    CustomArray mergeSort(CustomArray customArray) throws CustomArrayException;
    CustomArray quickSort(CustomArray customArray) throws CustomArrayException;
}