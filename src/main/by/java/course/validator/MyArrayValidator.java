package main.by.java.course.validator;

public interface MyArrayValidator {
    String VALID_STRING_REGEX = "([a-zA-Z]+\\s*)+";
    public boolean validateString(String string);
}
