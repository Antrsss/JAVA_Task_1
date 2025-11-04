package by.zgirskaya.course.reader.impl;

import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.reader.CustomStringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomStringReaderImpl implements CustomStringReader {
    private static final Logger logger = LogManager.getLogger(CustomStringReaderImpl.class);

    public String readStringFromFile(String filePath) throws CustomArrayException {
        logger.debug("Attempting to read file from: {}", filePath);

        if (filePath == null || filePath.isBlank()) {
            throw new CustomArrayException("File path cannot be null or empty");
        }

        try {
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                throw new CustomArrayException("File does not exist: " + filePath);
            }

            if (!Files.isRegularFile(path)) {
                throw new CustomArrayException("Path is not a file: " + filePath);
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
            throw new CustomArrayException("Error reading file: " + filePath, e);
        } catch (Exception e) {
            throw new CustomArrayException("Unexpected error while reading file: " + filePath, e);
        }
    }
}