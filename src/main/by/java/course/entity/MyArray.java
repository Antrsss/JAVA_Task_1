package main.by.java.course.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class MyArray {
    private static final Logger logger = LogManager.getLogger();

    private final String[] array;

    private MyArray(String[] array) {
        if (array != null) {
            this.array = Arrays.copyOf(array, array.length);
        } else {
            this.array = new String[0];
        }
        logger.debug("MyArray created with size: {}", this.array.length);
    }

    public String[] getMyArray() {
        return Arrays.copyOf(array, array.length);
    }

    public int getSize() {
        return array.length;
    }

    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public String toString() {
        return "MyArray{" + "array=" + Arrays.toString(array) + '}';
    }

    @Override
    public int hashCode() {
        if (array == null) {
            return -1;
        }
        if (array.length == 0) {
            return 0;
        }

        return 10 * array[0].hashCode();
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null) return false;

        if (getClass() != otherObject.getClass()) return false;

        MyArray myArray = (MyArray)otherObject;
        return this.array == myArray.array;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String[] array;

        public Builder setMyArray(String[] array) {
            if (array != null) {
                this.array = Arrays.copyOf(array, array.length);
            } else {
                this.array = new String[0];
            }

            return this;
        }

        public MyArray build() {
            logger.debug("Building MyArray with {} elements", array.length);
            return new MyArray(array);
        }
    }
}
