package by.zgirskaya.course.parser;

import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.parser.impl.CustomStringParserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CustomStringParserImplTest {

    private CustomStringParserImpl parser;

    @BeforeEach
    void setUp() {
        parser = new CustomStringParserImpl();
    }

    @Test
    @DisplayName("Parse null string throws MyArrayException")
    void testParseNullStringThrowsException() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> parser.parseLine(null));
        assertEquals("Input string cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Parse null and empty strings throws MyArrayException")
    void testParseNullOrEmptyStringsThrowsException(String input) {
        assertThrows(CustomArrayException.class, () -> parser.parseLine(input));
    }

    @Test
    @DisplayName("Parse blank string throws MyArrayException")
    void testParseBlankStringThrowsException() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> parser.parseLine("   "));
        assertEquals("Input string cannot be empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "123", "Test123", "special@chars",
            "string,with,commas"
    })
    @DisplayName("Parse invalid strings throws MyArrayException")
    void testParseInvalidStringsThrowsException(String invalidString) {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> parser.parseLine(invalidString));
        assertEquals("Invalid string cannot be converted to array!", exception.getMessage());
    }

    // Тесты на успешный парсинг (предполагая, что ELEMENT_DIVIDER_REGEX = ",")
    @Test
    @DisplayName("Parse single element string returns array with one element")
    void testParseSingleElementString() throws CustomArrayException {
        String[] result = parser.parseLine("Apple");

        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals("Apple", result[0]);
    }

    @Test
    @DisplayName("Parse multiple elements string returns correct array")
    void testParseMultipleElementsString() throws CustomArrayException {
        String[] result = parser.parseLine("Apple Banana Cherry");

        assertNotNull(result);
        assertEquals(3, result.length);
        assertArrayEquals(new String[]{"Apple", "Banana", "Cherry"}, result);
    }

    @Test
    @DisplayName("Parse string with multiple elements and no spaces")
    void testParseLineWithMultipleElementsNoSpaces() throws CustomArrayException {
        String[] result = parser.parseLine("ABCDE");

        assertNotNull(result);
        assertEquals(1, result.length);
        assertArrayEquals(new String[]{"ABCDE"}, result);
    }

    @Test
    @DisplayName("Parse string with consecutive delimiters")
    void testParseLineWithConsecutiveDelimiters() throws CustomArrayException {
        String[] result = parser.parseLine("Apple  Banana   Cherry");

        assertNotNull(result);
        assertTrue(result.length == 3);
        assertEquals("Apple", result[0]);
        assertEquals("Banana", result[1]);
        assertEquals("Cherry", result[2]);
    }

    @Test
    @DisplayName("Parse string with uppercase elements")
    void testParseLineWithUppercaseElements() throws CustomArrayException {
        String[] result = parser.parseLine("APPLE BANANA CHERRY");

        assertNotNull(result);
        assertEquals(3, result.length);
        assertArrayEquals(new String[]{"APPLE", "BANANA", "CHERRY"}, result);
    }

    @Test
    @DisplayName("Parse string with lowercase elements")
    void testParseLineWithLowercaseElements() throws CustomArrayException {
        String[] result = parser.parseLine("apple banana cherry");

        assertNotNull(result);
        assertEquals(3, result.length);
        assertArrayEquals(new String[]{"apple", "banana", "cherry"}, result);
    }

    @Test
    @DisplayName("Parse string with mixed case elements")
    void testParseLineWithMixedCaseElements() throws CustomArrayException {
        String[] result = parser.parseLine("Apple banana CHERRY");

        assertNotNull(result);
        assertEquals(3, result.length);
        assertArrayEquals(new String[]{"Apple", "banana", "CHERRY"}, result);
    }

    @Test
    @DisplayName("Parse string creates new array instance")
    void testParseLineCreatesNewArrayInstance() throws CustomArrayException {
        String[] result1 = parser.parseLine("Apple Banana");
        String[] result2 = parser.parseLine("Apple Banana");

        assertNotSame(result1, result2, "Each call should return new array instance");
        assertArrayEquals(result1, result2, "Arrays should have same content");
    }
}