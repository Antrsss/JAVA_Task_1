package test.by.java.course.validator;

import main.by.java.course.validator.MyArrayValidator;
import main.by.java.course.validator.impl.MyArrayValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayValidatorImplTest {

    private MyArrayValidator validator;

    @BeforeEach
    void setUp() {
        validator = new MyArrayValidatorImpl();
    }

    @Test
    @DisplayName("Validate null string returns false")
    void testValidateNullString() {
        assertFalse(validator.validateString(null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Validate null and empty strings returns false")
    void testValidateNullAndEmptyStrings(String input) {
        assertFalse(validator.validateString(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Apple", "Banana", "Cherry", "Date",
            "A", "B", "Z", "a", "z",
            "HelloWorld", "Multiple Words Here",
            "UPPERCASE", "lowercase", "MixedCase"
    })
    @DisplayName("Validate valid strings returns true")
    void testValidateValidStrings(String validString) {
        assertTrue(validator.validateString(validString),
                "String '" + validString + "' should be valid");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "123", "Test123", "123Test", "1", "0",
            "abc123def", "number42", "99bottles"
    })
    @DisplayName("Validate strings with digits returns false")
    void testValidateStringsWithDigits(String stringWithDigits) {
        assertFalse(validator.validateString(stringWithDigits),
                "String with digits '" + stringWithDigits + "' should be invalid");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "!", "@", "#", "$", "%", "^", "&", "*", "(", ")",
            "-", "_", "=", "+", "[", "]", "{", "}", "|", "\\",
            ";", ":", "'", "\"", ",", ".", "<", ">", "/", "?",
            "test-string", "email@domain.com", "file_name.txt",
            "price$100", "user@name", "path/to/file"
    })
    @DisplayName("Validate strings with special characters returns false")
    void testValidateStringsWithSpecialCharacters(String stringWithSpecialChars) {
        assertFalse(validator.validateString(stringWithSpecialChars),
                "String with special characters '" + stringWithSpecialChars + "' should be invalid");
    }

    @Test
    @DisplayName("Validate single uppercase letter returns true")
    void testValidateSingleUppercaseLetter() {
        assertTrue(validator.validateString("A"));
    }

    @Test
    @DisplayName("Validate single lowercase letter returns true")
    void testValidateSingleLowercaseLetter() {
        assertTrue(validator.validateString("a"));
    }

    @Test
    @DisplayName("Validate very long valid string returns true")
    void testValidateVeryLongValidString() {
        String longString = "ThisIsAVeryLongStringWithoutAnySpacesOrSpecialCharacters";
        assertTrue(validator.validateString(longString));
    }

    @Test
    @DisplayName("Validate mixed case string returns true")
    void testValidateMixedCaseString() {
        assertTrue(validator.validateString("MixedCaseString"));
    }

    @Test
    @DisplayName("Validate all uppercase string returns true")
    void testValidateAllUppercaseString() {
        assertTrue(validator.validateString("UPPERCASE"));
    }

    @Test
    @DisplayName("Validate all lowercase string returns true")
    void testValidateAllLowercaseString() {
        assertTrue(validator.validateString("lowercase"));
    }

    @Test
    @DisplayName("Validate empty string returns false")
    void testValidateEmptyString() {
        assertFalse(validator.validateString(""));
    }

    @Test
    @DisplayName("Validate string with only spaces returns false")
    void testValidateStringWithOnlySpaces() {
        assertFalse(validator.validateString("   "));
    }
}