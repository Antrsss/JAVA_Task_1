package main.by.java.course.exception;

public class MyArrayException extends Exception {

    public MyArrayException() {
        super();
    }

    public MyArrayException(String message) {
        super(message);
    }

    public MyArrayException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyArrayException(Throwable cause) {
        super(cause);
    }
}
