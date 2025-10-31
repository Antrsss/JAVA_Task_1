package main.by.java.course.observer;

import main.by.java.course.entity.MyArray;

public interface MyArrayObserver {
    void handleEvent(MyArray myArray);
}
