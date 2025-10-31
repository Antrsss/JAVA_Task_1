package main.by.java.course.specification;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;

public interface MyArraySpecification {
    boolean specify(MyArray myArray) throws MyArrayException;
}
