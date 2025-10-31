package main.by.java.course.specification.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.specification.MyArraySpecification;

public record IdSpecification(long id) implements MyArraySpecification {
    
    @Override
    public boolean specify(MyArray myArray) {
        return id == myArray.getId();
    }
}
