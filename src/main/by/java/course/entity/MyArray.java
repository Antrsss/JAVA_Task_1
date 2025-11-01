package main.by.java.course.entity;

import main.by.java.course.observer.MyArrayObservable;
import main.by.java.course.observer.MyArrayObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class MyArray implements MyArrayObservable {
    private static final Logger logger = LogManager.getLogger();
    private static int idCounter = 1;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String[] array;

        public Builder setMyArray(String[] array) {
            if (array == null) {
                this.array = new String[0];
            } else {
                this.array = Arrays.copyOf(array, array.length);
            }

            return this;
        }

        public MyArray build() {
            logger.debug("Building MyArray with {} elements", array.length);
            return new MyArray(array);
        }
    }

    private int id;
    private String[] array;
    private MyArrayObserver observer;

    private MyArray(String[] array) {
        if (array == null) {
            this.array = new String[0];
        } else {
            this.array = Arrays.copyOf(array, array.length);
        }
        id = nextId();
        idCounter++;

        logger.debug("MyArray created with size: {}", this.array.length);
    }

    public int getId() {
        return this.id;
    }

    private static int nextId() {
        return (idCounter + 1);
    }

    public String[] getMyArray() {
        return Arrays.copyOf(array, array.length);
    }

    public void setMyArray(String[] array) {
        if (array == null) {
            this.array = new String[0];
            return;
        }

        for (var string : array) {
            if (string == null || string.isBlank()) {
                return;
            }
        }

        this.array = Arrays.copyOf(array, array.length);
        notifyObservers();
    }

    public void setValueAtIndexOf(String string, int index) {
        if (index >= 0 && string != null && index < string.length() && !string.isBlank()) {
            this.array[index] = string;
        }
        notifyObservers();
    }

    public boolean isEmpty() {
        return array.length == 0;
    }

    public int calculateArrayElementAtIndexOf(int index) {
        String str = this.array[index];
        int value = 0;
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                value += c;
            } else if (c >= 'a' && c <= 'z') {
                value -= c;
            }
        }

        return value;
    }

    @Override
    public String toString() {
        return String.format("MyArray { array = %s, id = %d }", Arrays.toString(array), id);
    }

    @Override
    public int hashCode() {
        if (array == null) { return -1; }
        if (array.length == 0) { return 0; }

        int hashCode = 0;
        for (var string : array) {
            hashCode += string.hashCode();
        }

        return hashCode;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }

        MyArray myArray = (MyArray)otherObject;

        if (this.array.length != myArray.getMyArray().length){
            return false;
        }

        for (int i = 0; i < this.array.length; i++) {
            String thisElement = this.array[i];
            String otherElement = myArray.array[i];

            if (thisElement == null || !thisElement.equals(otherElement)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void attach(MyArrayObserver observer) {
        this.observer = observer;
    }

    @Override
    public void detach(MyArrayObserver observer) {
        this.observer = null;
    }

    @Override
    public void notifyObservers() {
        if (observer != null) {
            observer.handleEvent(this);
        }
    }
}
