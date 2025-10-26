package main.by.java.course.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ArrayValidator {
    private static final String VALID_STRING_REGEX = "[a-zA-Z]+";
    private static final Logger logger = LogManager.getLogger();

    public static List<String> filterValidStrings(String[] strings) {
        List<String> correctStrings = new ArrayList<>();

        for (var str : strings) {
            if (str.matches(VALID_STRING_REGEX)) {
                correctStrings.add(str);
            }
        }

        return correctStrings;
    }
}
