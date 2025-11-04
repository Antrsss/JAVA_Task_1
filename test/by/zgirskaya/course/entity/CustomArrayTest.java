package by.zgirskaya.course.entity;

import by.zgirskaya.course.observer.CustomArrayObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomArrayTest {

    private CustomArray customArray;
    private CustomArrayObserver observer;

    @BeforeEach
    void setUp() {
        String[] testData = {"Apple", "Banana", "Cherry"};
        customArray = CustomArray.newBuilder()
                .setMyArray(testData)
                .build();

        observer = mock(CustomArrayObserver.class);
    }

    @Test
    @DisplayName("Create MyArray with valid data using Builder")
    void testCreateMyArrayWithBuilder() {
        String[] input = {"A", "B", "C"};
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(input)
                .build();

        assertNotNull(array);
        assertFalse(array.isEmpty());
        assertEquals(3, array.getMyArray().length);
        assertArrayEquals(input, array.getMyArray());
    }

    @Test
    @DisplayName("Create MyArray with null array using Builder")
    void testCreateMyArrayWithNullArray() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(null)
                .build();

        assertNotNull(array);
        assertTrue(array.isEmpty());
        assertEquals(0, array.getMyArray().length);
    }

    @Test
    @DisplayName("Create MyArray with empty array using Builder")
    void testCreateMyArrayWithEmptyArray() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertNotNull(array);
        assertTrue(array.isEmpty());
        assertEquals(0, array.getMyArray().length);
    }

    @Test
    @DisplayName("GetMyArray returns copy of internal array")
    void testGetMyArrayReturnsCopy() {
        String[] original = customArray.getMyArray();
        original[0] = "Modified";

        String[] internal = customArray.getMyArray();
        assertNotSame(original, internal);
        assertEquals("Apple", internal[0]);
    }

    @Test
    @DisplayName("SetMyArray with valid data")
    void testSetMyArrayWithValidData() {
        String[] newData = {"X", "Y", "Z"};
        customArray.setMyArray(newData);

        assertArrayEquals(newData, customArray.getMyArray());
    }

    @Test
    @DisplayName("SetMyArray with null array returns empty array and does nothing to the source array")
    void testSetMyArrayWithNullArray() {
        String[] original = customArray.getMyArray().clone();
        String[] original_check = customArray.getMyArray().clone();
        customArray.setMyArray(null);

        assertArrayEquals(new String[0], customArray.getMyArray());
        assertArrayEquals(original, original_check);
    }

    @Test
    @DisplayName("SetMyArray with array containing null element does nothing")
    void testSetMyArrayWithNullElement() {
        String[] original = customArray.getMyArray().clone();
        String[] invalidData = {"A", null, "C"};
        customArray.setMyArray(invalidData);

        assertArrayEquals(original, customArray.getMyArray());
    }

    @Test
    @DisplayName("SetMyArray with array containing blank element does nothing")
    void testSetMyArrayWithBlankElement() {
        String[] original = customArray.getMyArray().clone();
        String[] invalidData = {"A", "   ", "C"};
        customArray.setMyArray(invalidData);

        assertArrayEquals(original, customArray.getMyArray());
    }

    @Test
    @DisplayName("SetValueAtIndexOf with valid parameters")
    void testSetValueAtIndexOfWithValidParameters() {
        customArray.setValueAtIndexOf("Orange", 1);

        String[] result = customArray.getMyArray();
        assertEquals("Orange", result[1]);
        assertEquals("Apple", result[0]);
        assertEquals("Cherry", result[2]);
    }

    @Test
    @DisplayName("SetValueAtIndexOf with null value does nothing")
    void testSetValueAtIndexOfWithNullValue() {
        String[] original = customArray.getMyArray().clone();
        customArray.setValueAtIndexOf(null, 1);

        assertArrayEquals(original, customArray.getMyArray());
    }

    @Test
    @DisplayName("SetValueAtIndexOf with blank value does nothing")
    void testSetValueAtIndexOfWithBlankValue() {
        String[] original = customArray.getMyArray().clone();
        customArray.setValueAtIndexOf("   ", 1);

        assertArrayEquals(original, customArray.getMyArray());
    }

    @Test
    @DisplayName("SetValueAtIndexOf with negative index does nothing")
    void testSetValueAtIndexOfWithNegativeIndex() {
        String[] original = customArray.getMyArray().clone();
        customArray.setValueAtIndexOf("Orange", -1);

        assertArrayEquals(original, customArray.getMyArray());
    }

    @Test
    @DisplayName("SetValueAtIndexOf with out of bounds index does nothing")
    void testSetValueAtIndexOfWithOutOfBoundsIndex() {
        String[] original = customArray.getMyArray().clone();
        customArray.setValueAtIndexOf("Orange", 10);

        assertArrayEquals(original, customArray.getMyArray());
    }

    @Test
    @DisplayName("IsEmpty returns true for empty array")
    void testIsEmptyWithEmptyArray() {
        CustomArray emptyArray = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertTrue(emptyArray.isEmpty());
    }

    @Test
    @DisplayName("IsEmpty returns false for non-empty array")
    void testIsEmptyWithNonEmptyArray() {
        assertFalse(customArray.isEmpty());
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with uppercase letters")
    void testCalculateArrayElementAtIndexOfWithUppercase() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[]{"ABC"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        int expected = 'A' + 'B' + 'C';
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with lowercase letters")
    void testCalculateArrayElementAtIndexOfWithLowercase() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[]{"abc"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        int expected = -'a' - 'b' - 'c';
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with mixed case letters")
    void testCalculateArrayElementAtIndexOfWithMixedCase() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[]{"AbC"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        int expected = 'A' - 'b' + 'C';
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf ignores non-letter characters")
    void testCalculateArrayElementAtIndexOfWithNonLetters() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[]{"A1b2C3"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        int expected = 'A' - 'b' + 'C';
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Attach observer successfully")
    void testAttachObserver() {
        customArray.attach(observer);

        customArray.notifyObservers();
        verify(observer, times(1)).handleEvent(customArray);
    }

    @Test
    @DisplayName("Detach observer successfully")
    void testDetachObserver() {
        customArray.attach(observer);
        customArray.detach(observer);

        customArray.notifyObservers();
        verify(observer, never()).handleEvent(customArray);
    }

    @Test
    @DisplayName("Notify observers on setMyArray")
    void testNotifyObserversOnSetMyArray() {
        customArray.attach(observer);
        customArray.setMyArray(new String[]{"X", "Y"});

        verify(observer, times(1)).handleEvent(customArray);
    }

    @Test
    @DisplayName("Notify observers on setValueAtIndexOf")
    void testNotifyObserversOnSetValueAtIndexOf() {
        customArray.attach(observer);
        customArray.setValueAtIndexOf("Orange", 1);

        verify(observer, times(1)).handleEvent(customArray);
    }

    @Test
    @DisplayName("No notification when observer is not attached")
    void testNoNotificationWithoutObserver() {
        customArray.setMyArray(new String[]{"X", "Y"});

        verify(observer, never()).handleEvent(customArray);
    }

    @Test
    @DisplayName("Equals returns true for same object")
    void testEqualsSameObject() {
        assertTrue(customArray.equals(customArray));
    }

    @Test
    @DisplayName("Equals returns true for arrays with same content")
    void testEqualsWithSameContent() {
        CustomArray array1 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();
        CustomArray array2 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();

        assertTrue(array1.equals(array2));
    }

    @Test
    @DisplayName("Equals returns false for arrays with different content")
    void testEqualsWithDifferentContent() {
        CustomArray array1 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();
        CustomArray array2 = CustomArray.newBuilder()
                .setMyArray(new String[]{"X", "Y", "Z"})
                .build();

        assertFalse(array1.equals(array2));
    }

    @Test
    @DisplayName("Equals returns false for arrays with different lengths")
    void testEqualsWithDifferentLengths() {
        CustomArray array1 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B"})
                .build();
        CustomArray array2 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();

        assertFalse(array1.equals(array2));
    }

    @Test
    @DisplayName("Equals returns false for null")
    void testEqualsWithNull() {
        assertFalse(customArray.equals(null));
    }

    @Test
    @DisplayName("Equals returns false for different class")
    void testEqualsWithDifferentClass() {
        assertFalse(customArray.equals("Not a MyArray"));
    }

    @Test
    @DisplayName("HashCode consistent with equals")
    void testHashCodeConsistentWithEquals() {
        CustomArray array1 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();
        CustomArray array2 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();

        assertEquals(array1.hashCode(), array2.hashCode());
    }

    @Test
    @DisplayName("HashCode for null array")
    void testHashCodeForNullArray() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(null)
                .build();

        assertEquals(0, array.hashCode());
    }

    @Test
    @DisplayName("HashCode for empty array")
    void testHashCodeForEmptyArray() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertEquals(0, array.hashCode());
    }

    @Test
    @DisplayName("ToString contains array and id information")
    void testToString() {
        String result = customArray.toString();

        assertTrue(result.contains("MyArray"));
        assertTrue(result.contains("array ="));
        assertTrue(result.contains("id ="));
        assertTrue(result.contains("Apple"));
        assertTrue(result.contains("Banana"));
        assertTrue(result.contains("Cherry"));
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with empty string")
    void testCalculateArrayElementAtIndexOfWithEmptyString() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[]{""})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with special characters only")
    void testCalculateArrayElementAtIndexOfWithSpecialCharacters() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[]{"123!@#"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        assertEquals(0, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("SetMyArray with various invalid inputs returns empty array")
    void testSetMyArrayWithVariousInvalidInputs(String[] invalidArray) {
        customArray.setMyArray(invalidArray);

        assertArrayEquals(new String[0], customArray.getMyArray());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    @DisplayName("SetValueAtIndexOf with various blank values")
    void testSetValueAtIndexOfWithVariousBlankValues(String blankValue) {
        String[] original = customArray.getMyArray().clone();
        customArray.setValueAtIndexOf(blankValue, 1);

        assertArrayEquals(original, customArray.getMyArray());
    }
}
