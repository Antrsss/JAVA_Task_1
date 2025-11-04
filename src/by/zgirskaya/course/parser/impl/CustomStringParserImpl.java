package by.zgirskaya.course.parser.impl;

import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.parser.CustomStringParser;
import by.zgirskaya.course.validator.impl.CustomArrayValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CustomStringParserImpl implements CustomStringParser {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String[][] parseFile(String fileContent) throws CustomArrayException {
        logger.debug("Parsing file content into arrays of arrays");

        if (fileContent == null) {
            throw new CustomArrayException("File content cannot be null");
        }

        if (fileContent.isBlank()) {
            throw new CustomArrayException("File content cannot be empty");
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
            } catch (CustomArrayException e) {
                logger.warn("Line {} contains no valid elements and will be skipped: {}", i, e.getMessage());
            }
        }

        if (arraysList.isEmpty()) {
            throw new CustomArrayException("No valid arrays found in file content!");
        }

        String[][] result = arraysList.toArray(new String[0][]);
        logger.info("Successfully parsed {} arrays from file content", validArraysCount);

        return result;
    }

    @Override
    public String[] parseLine(String string) throws CustomArrayException {
        logger.debug("Splitting string into array: '{}'", string);

        if (string == null) {
            throw new CustomArrayException("Input string cannot be null");
        }

        if (string.isBlank()) {
            throw new CustomArrayException("Input string cannot be empty");
        }

        CustomArrayValidatorImpl validator = new CustomArrayValidatorImpl();

        if (!validator.validateString(string)) {
            throw new CustomArrayException("Invalid string cannot be converted to array!");
        }

        String[] result = string.split(ELEMENT_DIVIDER_REGEX);
        logger.debug("Successfully split string into {} elements", result.length);

        return result;
    }
}