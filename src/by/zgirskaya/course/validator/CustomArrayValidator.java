package by.zgirskaya.course.validator;

public interface CustomArrayValidator {
    String VALID_STRING_REGEX = "([a-zA-Z]+\\s*)+";
    boolean validateString(String string);
}
