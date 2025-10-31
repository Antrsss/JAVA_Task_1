package main.by.java.course.repository.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.repository.MyArrayRepository;
import main.by.java.course.specification.MyArraySpecification;

import java.util.*;

public class MyArrayRepositoryImpl implements MyArrayRepository {

    private List<MyArray> myArrays = new ArrayList<>();

    @Override
    public boolean addMyArray(MyArray myArray) {
        return myArrays.add(myArray);
    }

    @Override
    public boolean removeMyArray(MyArray myArray) {
        return myArrays.remove(myArray);
    }

    @Override
    public void sort(Comparator<? super MyArray> c) {
        myArrays.sort(c);
    }

    @Override
    public List<MyArray> query(MyArraySpecification specification) {
        return List.of();
    }
}
