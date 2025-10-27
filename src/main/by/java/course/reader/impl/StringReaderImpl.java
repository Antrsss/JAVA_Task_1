package main.by.java.course.reader.impl;

import main.by.java.course.exception.MyArrayException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StringReaderImpl {
    private static final Logger logger = LogManager.getLogger(StringReaderImpl.class);

    public String readString(String filePath) throws MyArrayException {
        logger.debug("Attempting to read file from: {}", filePath);

        if (filePath == null || filePath.isBlank()) {
            throw new MyArrayException("File path cannot be null or empty");
        }

        try {
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                throw new MyArrayException("File does not exist: " + filePath);
            }

            if (!Files.isRegularFile(path)) {
                throw new MyArrayException("Path is not a file: " + filePath);
            }

            long fileSize = Files.size(path);
            if (fileSize == 0) {
                logger.warn("File is empty: {}", filePath);
                return "";
            }

            String content = new String(Files.readAllBytes(path)).trim();
            logger.debug("Successfully read {} characters from file: {}", content.length(), filePath);

            return content;

        } catch (IOException e) {
            throw new MyArrayException("Error reading file: " + filePath, e);
        } catch (Exception e) {
            throw new MyArrayException("Unexpected error while reading file: " + filePath, e);
        }
    }
}