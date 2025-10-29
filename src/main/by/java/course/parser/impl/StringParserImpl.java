package main.by.java.course.parser.impl;

import main.by.java.course.exception.MyArrayException;
import main.by.java.course.parser.StringParser;
import main.by.java.course.validator.impl.MyArrayValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class StringParserImpl implements StringParser {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String[][] parseFile(String fileContent) throws MyArrayException {
        logger.debug("Parsing file content into arrays of arrays");

        if (fileContent == null) {
            throw new MyArrayException("File content cannot be null");
        }

        if (fileContent.isBlank()) {
            throw new MyArrayException("File content cannot be empty");
        }

        String[] lines = fileContent.split(ARRAY_DIVIDER_REGEX);
        List<String[]> arraysList = new ArrayList<>();

        int validArraysCount = 0;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            if (line.isEmpty()) {
                logger.debug("Skipping empty line {}", i);
                continue;
            }

            try {
                String[] array = parseLine(line);
                if (array.length > 0) {
                    arraysList.add(array);
                    validArraysCount++;
                    logger.debug("Line {} parsed into array with {} elements", i, array.length);
                }
            } catch (MyArrayException e) {
                logger.warn("Line {} contains no valid elements and will be skipped: {}", i, e.getMessage());
            }
        }

        if (arraysList.isEmpty()) {
            throw new MyArrayException("No valid arrays found in file content!");
        }

        String[][] result = arraysList.toArray(new String[0][]);
        logger.info("Successfully parsed {} arrays from file content", validArraysCount);

        return result;
    }

    @Override
    public String[] parseLine(String string) throws MyArrayException {
        logger.debug("Splitting string into array: '{}'", string);

        if (string == null) {
            throw new MyArrayException("Input string cannot be null");
        }

        if (string.isBlank()) {
            throw new MyArrayException("Input string cannot be empty");
        }

        MyArrayValidatorImpl validator = new MyArrayValidatorImpl();

        if (!validator.validateString(string)) {
            throw new MyArrayException("Invalid string cannot be converted to array!");
        }

        String[] result = string.split(ELEMENT_DIVIDER_REGEX);
        logger.debug("Successfully split string into {} elements", result.length);

        return result;
    }


}
