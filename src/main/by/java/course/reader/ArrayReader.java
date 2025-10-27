package main.by.java.course.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ArrayReader {
    private static final String ELEMENT_REGEX = "\\s+";
    private static final Logger logger = LogManager.getLogger();

    public String readString(String filePath) {

        logger.debug("Attempting to read file from: {}", filePath);

        if (filePath == null || filePath.isBlank()) {
            logger.error("File path is null or empty");
            return null;
        }

        try {
            Path path = Paths.get(filePath);

            // Проверяем существование файла
            if (!Files.exists(path)) {
                logger.error("File does not exist: {}", filePath);
                return null;
            }

            // Проверяем, что это файл, а не директория
            if (!Files.isRegularFile(path)) {
                logger.error("Path is not a file: {}", filePath);
                return null;
            }
            // Проверяем размер файла
            long fileSize = Files.size(path);
            if (fileSize == 0) {
                logger.warn("File is empty: {}", filePath);
                return "";
            }

            // Читаем все содержимое файла как строку
            String content = new String(Files.readAllBytes(path)).trim();
            logger.debug("Successfully read {} characters from file: {}", content.length(), filePath);

            return content;

        } catch (IOException e) {
            logger.error("Error reading file: {}. Message: {}", filePath, e.getMessage());
            return null;
        } catch (SecurityException e) {
            logger.error("Security exception while accessing file: {}. Message: {}", filePath, e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error while reading file: {}. Message: {}", filePath, e.getMessage());
            return null;
        }
    }

    public String[] readArray(String string) {
        if (string == null) {

        }

        if (string.isBlank()) {

        }

        return string.split(ELEMENT_REGEX);
    }
}
