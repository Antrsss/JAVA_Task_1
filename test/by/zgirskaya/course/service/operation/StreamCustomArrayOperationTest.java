package by.zgirskaya.course.service.operation;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.operation.impl.StreamCustomArrayOperationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamCustomArrayOperationTest {
    private StreamCustomArrayOperationImpl operation;
    private CustomArray emptyArray;
    private CustomArray singleElementArray;
    private CustomArray multiElementArray;
    private CustomArray mixedCaseArray;
    private CustomArray arrayWithNull;

    @BeforeEach
    void setUp() {
        operation = new StreamCustomArrayOperationImpl();

        emptyArray = CustomArray.newBuilder()
                .setMyArray(new String[]{})
                .build();

        singleElementArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"Test"})
                .build();

        multiElementArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Banana", "Cherry", "Date"})
                .build();

        mixedCaseArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"ABC", "def", "GHI", "jkl"})
                .build();

        arrayWithNull = CustomArray.newBuilder()
                .setMyArray(new String[]{"First", null, "Third"})
                .build();
    }

    @Test
    @DisplayName("Test findMinValue with valid array")
    void testFindMinValueWithValidArray() throws CustomArrayException {
        String minValue = operation.findMinValue(multiElementArray);

        assertEquals("Cherry", minValue, "Should find correct min value");
    }

    @Test
    @DisplayName("Test findMinValue with single element array")
    void testFindMinValueWithSingleElement() throws CustomArrayException {
        String minValue = operation.findMinValue(singleElementArray);

        assertEquals("Test", minValue, "Should return the only element");
    }

    @Test
    @DisplayName("Test findMinValue with null array throws exception")
    void testFindMinValueWithNullArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.findMinValue(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test findMinValue with empty array throws exception")
    void testFindMinValueWithEmptyArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.findMinValue(emptyArray));

        assertEquals("Cannot find min value in empty array", exception.getMessage());
    }

    @Test
    @DisplayName("Test findMinValue with mixed case array")
    void testFindMinValueWithMixedCase() throws CustomArrayException {
        String minValue = operation.findMinValue(mixedCaseArray);

        assertEquals("jkl", minValue, "Should handle mixed case correctly");
    }

    @Test
    @DisplayName("Test findMaxValue with valid array")
    void testFindMaxValueWithValidArray() throws CustomArrayException {
        String maxValue = operation.findMaxValue(multiElementArray);

        assertEquals("Date", maxValue, "Should find correct max value");
    }

    @Test
    @DisplayName("Test findMaxValue with single element array")
    void testFindMaxValueWithSingleElement() throws CustomArrayException {
        String maxValue = operation.findMaxValue(singleElementArray);

        assertEquals("Test", maxValue, "Should return the only element");
    }

    @Test
    @DisplayName("Test findMaxValue with null array throws exception")
    void testFindMaxValueWithNullArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.findMaxValue(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test findMaxValue with empty array throws exception")
    void testFindMaxValueWithEmptyArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.findMaxValue(emptyArray));

        assertEquals("Cannot find max value in empty array", exception.getMessage());
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with valid parameters")
    void testReplaceCustomArrayElementValid() throws CustomArrayException {
        CustomArray result = operation.replaceCustomArrayElement(multiElementArray, "Replaced", 1);
        String[] expected = {"Apple", "Replaced", "Cherry", "Date"};

        assertArrayEquals(expected, result.getMyArray(), "Should replace element at specified index");
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with first index")
    void testReplaceCustomArrayElementFirstIndex() throws CustomArrayException {
        CustomArray result = operation.replaceCustomArrayElement(multiElementArray, "First", 0);

        assertEquals("First", result.getMyArray()[0], "Should replace first element");
        assertEquals("Banana", result.getMyArray()[1], "Other elements should remain unchanged");
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with last index")
    void testReplaceCustomArrayElementLastIndex() throws CustomArrayException {
        CustomArray result = operation.replaceCustomArrayElement(multiElementArray, "Last", 3);

        assertEquals("Last", result.getMyArray()[3], "Should replace last element");
        assertEquals("Apple", result.getMyArray()[0], "First element should remain unchanged");
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with null array throws exception")
    void testReplaceCustomArrayElementWithNullArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.replaceCustomArrayElement(null, "value", 0));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with null value throws exception")
    void testReplaceCustomArrayElementWithNullValue() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.replaceCustomArrayElement(multiElementArray, null, 0));

        assertEquals("Replacement value cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with negative index throws exception")
    void testReplaceCustomArrayElementWithNegativeIndex() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.replaceCustomArrayElement(multiElementArray, "value", -1));

        assertTrue(exception.getMessage().contains("Index out of bounds"));
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with out of bounds index throws exception")
    void testReplaceCustomArrayElementWithOutOfBoundsIndex() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.replaceCustomArrayElement(multiElementArray, "value", 10));

        assertTrue(exception.getMessage().contains("Index out of bounds"));
    }

    @Test
    @DisplayName("Test calculateAverageValue with known values")
    void testCalculateAverageValue() throws CustomArrayException {
        CustomArray testArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"ABC", "abc"})
                .build();

        double average = operation.calculateAverageValue(testArray);
        double expected = (double)('A' + 'B' + 'C' - 'a' - 'b' - 'c') / 2;

        assertEquals(expected, average, "Average should match expected calculation");
    }

    @Test
    @DisplayName("Test calculateAverageValue with null array throws exception")
    void testCalculateAverageValueWithNullArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.calculateAverageValue(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculateAverageValue with empty array throws exception")
    void testCalculateAverageValueWithEmptyArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.calculateAverageValue(emptyArray));

        assertEquals("Cannot calculate average for empty array", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculateSum with valid array")
    void testCalculateSum() throws CustomArrayException {
        int sum = operation.calculateSum(multiElementArray);
        int expected = -1546;

        assertEquals(expected, sum, "Sum of the multiElementArray should be " + expected);
    }

    @Test
    @DisplayName("Test calculateSum with empty array returns zero")
    void testCalculateSumWithEmptyArray() throws CustomArrayException {
        int sum = operation.calculateSum(emptyArray);

        assertEquals(0, sum, "Sum of empty array should be 0");
    }

    @Test
    @DisplayName("Test calculateSum with null array throws exception")
    void testCalculateSumWithNullArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.calculateSum(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculatePositiveValues with valid array")
    void testCalculatePositiveValues() throws CustomArrayException {
        int positiveCount = operation.calculatePositiveValues(mixedCaseArray);

        assertEquals(2, positiveCount, "Should count correct number of positive values");
    }

    @Test
    @DisplayName("Test calculatePositiveValues with empty array returns zero")
    void testCalculatePositiveValuesWithEmptyArray() throws CustomArrayException {
        int positiveCount = operation.calculatePositiveValues(emptyArray);

        assertEquals(0, positiveCount, "Empty array should have 0 positive values");
    }

    @Test
    @DisplayName("Test calculatePositiveValues with null array throws exception")
    void testCalculatePositiveValuesWithNullArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.calculatePositiveValues(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculateNegativeValues with valid array")
    void testCalculateNegativeValues() throws CustomArrayException {
        CustomArray negativeArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"abc", "def", "ghi"})
                .build();

        int negativeCount = operation.calculateNegativeValues(negativeArray);

        assertTrue(negativeCount > 0, "Should count negative values");
    }

    @Test
    @DisplayName("Test calculateNegativeValues with empty array returns zero")
    void testCalculateNegativeValuesWithEmptyArray() throws CustomArrayException {
        int negativeCount = operation.calculateNegativeValues(emptyArray);

        assertEquals(0, negativeCount, "Empty array should have 0 negative values");
    }

    @Test
    @DisplayName("Test calculateNegativeValues with null array throws exception")
    void testCalculateNegativeValuesWithNullArray() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> operation.calculateNegativeValues(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test immutability of original array after operations")
    void testImmutability() throws CustomArrayException {
        String[] original = multiElementArray.getMyArray().clone();

        operation.findMinValue(multiElementArray);
        operation.findMaxValue(multiElementArray);
        operation.calculateSum(multiElementArray);

        assertArrayEquals(original, multiElementArray.getMyArray(),
                "Original array should remain unchanged after operations");
    }

    @Test
    @DisplayName("Test replace creates new array without modifying original")
    void testReplaceCreatesNewArray() throws CustomArrayException {
        String[] original = multiElementArray.getMyArray().clone();

        CustomArray replaced = operation.replaceCustomArrayElement(multiElementArray, "New", 1);

        assertArrayEquals(original, multiElementArray.getMyArray(),
                "Original array should remain unchanged after replace");

        assertNotSame(multiElementArray, replaced, "Should return new MyArray instance");
        assertNotEquals(multiElementArray, replaced, "New array should be different");
    }

    @Test
    @DisplayName("Test consistency between different operations")
    void testOperationConsistency() throws CustomArrayException {
        CustomArray simpleArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"}) // A=65, B=66, C=67
                .build();

        int sum = operation.calculateSum(simpleArray);
        double average = operation.calculateAverageValue(simpleArray);

        assertEquals((double)sum / simpleArray.getMyArray().length, average,
                "Average should equal sum divided by length");
    }

    @Test
    @DisplayName("Test edge case with single character elements")
    void testSingleCharacterElements() throws CustomArrayException {
        CustomArray singleCharArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "a", "B", "b"})
                .build();

        int positiveCount = operation.calculatePositiveValues(singleCharArray);
        int negativeCount = operation.calculateNegativeValues(singleCharArray);
        int total = singleCharArray.getMyArray().length;

        assertEquals(2, positiveCount, "Should count uppercase as positive");
        assertEquals(2, negativeCount, "Should count lowercase as negative");
        assertEquals(total, positiveCount + negativeCount, "All elements should be counted");
    }
}
