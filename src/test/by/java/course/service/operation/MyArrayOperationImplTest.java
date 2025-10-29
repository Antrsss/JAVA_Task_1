package test.by.java.course.service.operation;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.service.operation.impl.MyArrayOperationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayOperationImplTest {
    private MyArrayOperationImpl operation;
    private MyArray emptyArray;
    private MyArray singleElementArray;
    private MyArray multiElementArray;
    private MyArray mixedCaseArray;
    private MyArray arrayWithNull;

    @BeforeEach
    void setUp() {
        operation = new MyArrayOperationImpl();

        emptyArray = MyArray.newBuilder()
                .setMyArray(new String[]{})
                .build();

        singleElementArray = MyArray.newBuilder()
                .setMyArray(new String[]{"Test"})
                .build();

        multiElementArray = MyArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Banana", "Cherry", "Date"})
                .build();

        mixedCaseArray = MyArray.newBuilder()
                .setMyArray(new String[]{"ABC", "def", "GHI", "jkl"})
                .build();

        arrayWithNull = MyArray.newBuilder()
                .setMyArray(new String[]{"First", null, "Third"})
                .build();
    }

    @Test
    @DisplayName("Test findMinValue with valid array")
    void testFindMinValueWithValidArray() throws MyArrayException {
        String minValue = operation.findMinValue(multiElementArray);

        assertEquals("Cherry", minValue, "Should find correct min value");
    }

    @Test
    @DisplayName("Test findMinValue with single element array")
    void testFindMinValueWithSingleElement() throws MyArrayException {
        String minValue = operation.findMinValue(singleElementArray);

        assertEquals("Test", minValue, "Should return the only element");
    }

    @Test
    @DisplayName("Test findMinValue with null array throws exception")
    void testFindMinValueWithNullArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.findMinValue(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test findMinValue with empty array throws exception")
    void testFindMinValueWithEmptyArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.findMinValue(emptyArray));

        assertEquals("Cannot find min value in empty array", exception.getMessage());
    }

    @Test
    @DisplayName("Test findMinValue with mixed case array")
    void testFindMinValueWithMixedCase() throws MyArrayException {
        String minValue = operation.findMinValue(mixedCaseArray);

        assertEquals("jkl", minValue, "Should handle mixed case correctly");
    }

    @Test
    @DisplayName("Test findMaxValue with valid array")
    void testFindMaxValueWithValidArray() throws MyArrayException {
        String maxValue = operation.findMaxValue(multiElementArray);

        assertEquals("Date", maxValue, "Should find correct max value");
    }

    @Test
    @DisplayName("Test findMaxValue with single element array")
    void testFindMaxValueWithSingleElement() throws MyArrayException {
        String maxValue = operation.findMaxValue(singleElementArray);

        assertEquals("Test", maxValue, "Should return the only element");
    }

    @Test
    @DisplayName("Test findMaxValue with null array throws exception")
    void testFindMaxValueWithNullArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.findMaxValue(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test findMaxValue with empty array throws exception")
    void testFindMaxValueWithEmptyArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.findMaxValue(emptyArray));

        assertEquals("Cannot find max value in empty array", exception.getMessage());
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with valid parameters")
    void testReplaceMyArrayElementValid() throws MyArrayException {
        MyArray result = operation.replaceMyArrayElement(multiElementArray, "Replaced", 1);
        String[] expected = {"Apple", "Replaced", "Cherry", "Date"};

        assertArrayEquals(expected, result.getMyArray(), "Should replace element at specified index");
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with first index")
    void testReplaceMyArrayElementFirstIndex() throws MyArrayException {
        MyArray result = operation.replaceMyArrayElement(multiElementArray, "First", 0);

        assertEquals("First", result.getMyArray()[0], "Should replace first element");
        assertEquals("Banana", result.getMyArray()[1], "Other elements should remain unchanged");
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with last index")
    void testReplaceMyArrayElementLastIndex() throws MyArrayException {
        MyArray result = operation.replaceMyArrayElement(multiElementArray, "Last", 3);

        assertEquals("Last", result.getMyArray()[3], "Should replace last element");
        assertEquals("Apple", result.getMyArray()[0], "First element should remain unchanged");
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with null array throws exception")
    void testReplaceMyArrayElementWithNullArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.replaceMyArrayElement(null, "value", 0));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with null value throws exception")
    void testReplaceMyArrayElementWithNullValue() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.replaceMyArrayElement(multiElementArray, null, 0));

        assertEquals("Replacement value cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with negative index throws exception")
    void testReplaceMyArrayElementWithNegativeIndex() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.replaceMyArrayElement(multiElementArray, "value", -1));

        assertTrue(exception.getMessage().contains("Index out of bounds"));
    }

    @Test
    @DisplayName("Test replaceMyArrayElement with out of bounds index throws exception")
    void testReplaceMyArrayElementWithOutOfBoundsIndex() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.replaceMyArrayElement(multiElementArray, "value", 10));

        assertTrue(exception.getMessage().contains("Index out of bounds"));
    }

    @Test
    @DisplayName("Test calculateAverageValue with known values")
    void testCalculateAverageValue() throws MyArrayException {
        MyArray testArray = MyArray.newBuilder()
                .setMyArray(new String[]{"ABC", "abc"})
                .build();

        double average = operation.calculateAverageValue(testArray);
        double expected = (double)('A' + 'B' + 'C' - 'a' - 'b' - 'c') / 2;

        assertEquals(expected, average, "Average should match expected calculation");
    }

    @Test
    @DisplayName("Test calculateAverageValue with null array throws exception")
    void testCalculateAverageValueWithNullArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.calculateAverageValue(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculateAverageValue with empty array throws exception")
    void testCalculateAverageValueWithEmptyArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.calculateAverageValue(emptyArray));

        assertEquals("Cannot calculate average for empty array", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculateSum with valid array")
    void testCalculateSum() throws MyArrayException {
        int sum = operation.calculateSum(multiElementArray);
        int expected = -1546;

        assertEquals(expected, sum, "Sum of the multiElementArray should be " + expected);
    }

    @Test
    @DisplayName("Test calculateSum with empty array returns zero")
    void testCalculateSumWithEmptyArray() throws MyArrayException {
        int sum = operation.calculateSum(emptyArray);

        assertEquals(0, sum, "Sum of empty array should be 0");
    }

    @Test
    @DisplayName("Test calculateSum with null array throws exception")
    void testCalculateSumWithNullArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.calculateSum(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculatePositiveValues with valid array")
    void testCalculatePositiveValues() throws MyArrayException {
        int positiveCount = operation.calculatePositiveValues(mixedCaseArray);

        assertEquals(2, positiveCount, "Should count correct number of positive values");
    }

    @Test
    @DisplayName("Test calculatePositiveValues with empty array returns zero")
    void testCalculatePositiveValuesWithEmptyArray() throws MyArrayException {
        int positiveCount = operation.calculatePositiveValues(emptyArray);

        assertEquals(0, positiveCount, "Empty array should have 0 positive values");
    }

    @Test
    @DisplayName("Test calculatePositiveValues with null array throws exception")
    void testCalculatePositiveValuesWithNullArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.calculatePositiveValues(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test calculateNegativeValues with valid array")
    void testCalculateNegativeValues() throws MyArrayException {
        MyArray negativeArray = MyArray.newBuilder()
                .setMyArray(new String[]{"abc", "def", "ghi"})
                .build();

        int negativeCount = operation.calculateNegativeValues(negativeArray);

        assertTrue(negativeCount > 0, "Should count negative values");
    }

    @Test
    @DisplayName("Test calculateNegativeValues with empty array returns zero")
    void testCalculateNegativeValuesWithEmptyArray() throws MyArrayException {
        int negativeCount = operation.calculateNegativeValues(emptyArray);

        assertEquals(0, negativeCount, "Empty array should have 0 negative values");
    }

    @Test
    @DisplayName("Test calculateNegativeValues with null array throws exception")
    void testCalculateNegativeValuesWithNullArray() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> operation.calculateNegativeValues(null));

        assertEquals("MyArray cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test array with null elements throws exception in calculations")
    void testArrayWithNullElementsThrowsException() {
        assertThrows(MyArrayException.class,
                () -> operation.calculateSum(arrayWithNull));
    }

    @Test
    @DisplayName("Test immutability of original array after operations")
    void testImmutability() throws MyArrayException {
        String[] original = multiElementArray.getMyArray().clone();

        operation.findMinValue(multiElementArray);
        operation.findMaxValue(multiElementArray);
        operation.calculateSum(multiElementArray);

        assertArrayEquals(original, multiElementArray.getMyArray(),
                "Original array should remain unchanged after operations");
    }

    @Test
    @DisplayName("Test replace creates new array without modifying original")
    void testReplaceCreatesNewArray() throws MyArrayException {
        String[] original = multiElementArray.getMyArray().clone();

        MyArray replaced = operation.replaceMyArrayElement(multiElementArray, "New", 1);

        assertArrayEquals(original, multiElementArray.getMyArray(),
                "Original array should remain unchanged after replace");

        assertNotSame(multiElementArray, replaced, "Should return new MyArray instance");
        assertNotEquals(multiElementArray, replaced, "New array should be different");
    }

    @Test
    @DisplayName("Test consistency between different operations")
    void testOperationConsistency() throws MyArrayException {
        MyArray simpleArray = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"}) // A=65, B=66, C=67
                .build();

        int sum = operation.calculateSum(simpleArray);
        double average = operation.calculateAverageValue(simpleArray);

        assertEquals((double)sum / simpleArray.getMyArray().length, average,
                "Average should equal sum divided by length");
    }

    @Test
    @DisplayName("Test edge case with single character elements")
    void testSingleCharacterElements() throws MyArrayException {
        MyArray singleCharArray = MyArray.newBuilder()
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
