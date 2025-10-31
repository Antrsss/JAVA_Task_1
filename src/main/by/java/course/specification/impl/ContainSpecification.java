package main.by.java.course.specification.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.specification.MyArraySpecification;

public record ContainSpecification(String myArrayElement) implements MyArraySpecification {

    @Override
    public boolean specify(MyArray myArray) {
        String[] array = myArray.getMyArray();

        for (var string : array) {
            if (string.equals(myArrayElement)) {
                return true;
            }
        }

        return false;
    }
}
