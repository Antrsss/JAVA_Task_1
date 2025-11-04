package by.zgirskaya.course.reader;

import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.reader.impl.CustomStringReaderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CustomStringReaderImplTest {

    private CustomStringReaderImpl reader;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        reader = new CustomStringReaderImpl();
    }

    @Test
    @DisplayName("Read from null file path throws MyArrayException")
    void testReadFromNullFilePathThrowsException() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> reader.readStringFromFile(null));
        assertEquals("File path cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Read from empty file path throws MyArrayException")
    void testReadFromEmptyFilePathThrowsException() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> reader.readStringFromFile(""));
        assertEquals("File path cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Read from blank file path throws MyArrayException")
    void testReadFromBlankFilePathThrowsException() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> reader.readStringFromFile("   "));
        assertEquals("File path cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("Read from existing file with content returns correct string")
    void testReadFromExistingFileWithContent() throws IOException, CustomArrayException {
        Path testFile = tempDir.resolve("test_file.txt");
        String expectedContent = "Apple,Banana,Cherry,Date";
        Files.writeString(testFile, expectedContent);

        String result = reader.readStringFromFile(testFile.toString());

        assertEquals(expectedContent, result);
    }

    @Test
    @DisplayName("Read from empty file returns empty string")
    void testReadFromEmptyFileReturnsEmptyString() throws IOException, CustomArrayException {
        Path testFile = tempDir.resolve("empty_file.txt");
        Files.createFile(testFile);

        String result = reader.readStringFromFile(testFile.toString());

        assertEquals("", result);
    }

    @Test
    @DisplayName("Read from file with whitespace trims content")
    void testReadFromFileWithWhitespaceTrimsContent() throws IOException, CustomArrayException {
        Path testFile = tempDir.resolve("whitespace_file.txt");
        Files.writeString(testFile, "   Apple,Banana   \n\n");

        String result = reader.readStringFromFile(testFile.toString());

        assertEquals("Apple,Banana", result);
    }

    @Test
    @DisplayName("Read from file with special characters")
    void testReadFromFileWithSpecialCharacters() throws IOException, CustomArrayException {
        Path testFile = tempDir.resolve("special_chars.txt");
        String content = "Line1\nLine2\tTabbed\nLine3 with spaces";
        Files.writeString(testFile, content);

        String result = reader.readStringFromFile(testFile.toString());

        assertEquals(content.trim(), result);
    }

    @Test
    @DisplayName("Read from file with absolute path")
    void testReadFromFileWithAbsolutePath() throws IOException, CustomArrayException {
        Path testFile = tempDir.resolve("absolute_test.txt");
        String content = "Absolute path content";
        Files.writeString(testFile, content);

        String result = reader.readStringFromFile(testFile.toAbsolutePath().toString());

        assertEquals(content, result);
    }

    @Test
    @DisplayName("Read from file with only whitespace")
    void testReadFromFileWithOnlyWhitespace() throws IOException, CustomArrayException {
        Path testFile = tempDir.resolve("only_whitespace.txt");
        Files.writeString(testFile, "   \n\t  \r\n   ");

        String result = reader.readStringFromFile(testFile.toString());

        assertEquals("", result);
    }

    @Test
    @DisplayName("Read from file with single character")
    void testReadFromFileWithSingleCharacter() throws IOException, CustomArrayException {
        Path testFile = tempDir.resolve("single_char.txt");
        Files.writeString(testFile, "A");

        String result = reader.readStringFromFile(testFile.toString());

        assertEquals("A", result);
    }

    @Test
    @DisplayName("Multiple reads from same file return same content")
    void testMultipleReadsFromSameFile() throws IOException, CustomArrayException {
        Path testFile = tempDir.resolve("multiple_reads.txt");
        String content = "Consistent Content";
        Files.writeString(testFile, content);

        String firstRead = reader.readStringFromFile(testFile.toString());
        String secondRead = reader.readStringFromFile(testFile.toString());
        String thirdRead = reader.readStringFromFile(testFile.toString());

        assertEquals(firstRead, secondRead);
        assertEquals(secondRead, thirdRead);
        assertEquals(content, firstRead);
    }
}