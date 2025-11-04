package by.zgirskaya.course.service.operation;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;

public interface CustomArrayOperation {
    String findMinValue(CustomArray customArray) throws CustomArrayException;
    String findMaxValue(CustomArray customArray) throws CustomArrayException;
    CustomArray replaceCustomArrayElement(CustomArray customArray, String value, int index) throws CustomArrayException;
    double calculateAverageValue(CustomArray customArray) throws CustomArrayException;
    int calculateSum(CustomArray customArray) throws CustomArrayException;
    int calculatePositiveValues(CustomArray customArray) throws CustomArrayException;
    int calculateNegativeValues(CustomArray customArray) throws CustomArrayException;
}