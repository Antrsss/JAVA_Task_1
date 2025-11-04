package by.zgirskaya.course.exception;

public class CustomArrayException extends Exception {

    public CustomArrayException() {
        super();
    }

    public CustomArrayException(String message) {
        super(message);
    }

    public CustomArrayException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomArrayException(Throwable cause) {
        super(cause);
    }
}
