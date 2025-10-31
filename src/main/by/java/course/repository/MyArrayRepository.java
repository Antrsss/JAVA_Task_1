package main.by.java.course.repository;

import main.by.java.course.entity.MyArray;
import main.by.java.course.specification.MyArraySpecification;

import java.util.Comparator;
import java.util.List;

public interface MyArrayRepository {
    boolean addMyArray(MyArray myArray);
    boolean removeMyArray(MyArray myArray);
    void sort(Comparator<? super MyArray> c);

    List<MyArray> query(MyArraySpecification specification);
}
