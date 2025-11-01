package test.by.java.course.entity;

import main.by.java.course.entity.MyArray;
import main.by.java.course.observer.MyArrayObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MyArrayTest {

    private MyArray myArray;
    private MyArrayObserver observer;

    @BeforeEach
    void setUp() {
        String[] testData = {"Apple", "Banana", "Cherry"};
        myArray = MyArray.newBuilder()
                .setMyArray(testData)
                .build();

        observer = mock(MyArrayObserver.class);
    }

    @Test
    @DisplayName("Create MyArray with valid data using Builder")
    void testCreateMyArrayWithBuilder() {
        String[] input = {"A", "B", "C"};
        MyArray array = MyArray.newBuilder()
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
        MyArray array = MyArray.newBuilder()
                .setMyArray(null)
                .build();

        assertNotNull(array);
        assertTrue(array.isEmpty());
        assertEquals(0, array.getMyArray().length);
    }

    @Test
    @DisplayName("Create MyArray with empty array using Builder")
    void testCreateMyArrayWithEmptyArray() {
        MyArray array = MyArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertNotNull(array);
        assertTrue(array.isEmpty());
        assertEquals(0, array.getMyArray().length);
    }

    @Test
    @DisplayName("GetMyArray returns copy of internal array")
    void testGetMyArrayReturnsCopy() {
        String[] original = myArray.getMyArray();
        original[0] = "Modified";

        String[] internal = myArray.getMyArray();
        assertNotSame(original, internal);
        assertEquals("Apple", internal[0]);
    }

    @Test
    @DisplayName("SetMyArray with valid data")
    void testSetMyArrayWithValidData() {
        String[] newData = {"X", "Y", "Z"};
        myArray.setMyArray(newData);

        assertArrayEquals(newData, myArray.getMyArray());
    }

    @Test
    @DisplayName("SetMyArray with null array returns empty array and does nothing to the source array")
    void testSetMyArrayWithNullArray() {
        String[] original = myArray.getMyArray().clone();
        String[] original_check = myArray.getMyArray().clone();
        myArray.setMyArray(null);

        assertArrayEquals(new String[0], myArray.getMyArray());
        assertArrayEquals(original, original_check);
    }

    @Test
    @DisplayName("SetMyArray with array containing null element does nothing")
    void testSetMyArrayWithNullElement() {
        String[] original = myArray.getMyArray().clone();
        String[] invalidData = {"A", null, "C"};
        myArray.setMyArray(invalidData);

        assertArrayEquals(original, myArray.getMyArray());
    }

    @Test
    @DisplayName("SetMyArray with array containing blank element does nothing")
    void testSetMyArrayWithBlankElement() {
        String[] original = myArray.getMyArray().clone();
        String[] invalidData = {"A", "   ", "C"};
        myArray.setMyArray(invalidData);

        assertArrayEquals(original, myArray.getMyArray());
    }

    @Test
    @DisplayName("SetValueAtIndexOf with valid parameters")
    void testSetValueAtIndexOfWithValidParameters() {
        myArray.setValueAtIndexOf("Orange", 1);

        String[] result = myArray.getMyArray();
        assertEquals("Orange", result[1]);
        assertEquals("Apple", result[0]);
        assertEquals("Cherry", result[2]);
    }

    @Test
    @DisplayName("SetValueAtIndexOf with null value does nothing")
    void testSetValueAtIndexOfWithNullValue() {
        String[] original = myArray.getMyArray().clone();
        myArray.setValueAtIndexOf(null, 1);

        assertArrayEquals(original, myArray.getMyArray());
    }

    @Test
    @DisplayName("SetValueAtIndexOf with blank value does nothing")
    void testSetValueAtIndexOfWithBlankValue() {
        String[] original = myArray.getMyArray().clone();
        myArray.setValueAtIndexOf("   ", 1);

        assertArrayEquals(original, myArray.getMyArray());
    }

    @Test
    @DisplayName("SetValueAtIndexOf with negative index does nothing")
    void testSetValueAtIndexOfWithNegativeIndex() {
        String[] original = myArray.getMyArray().clone();
        myArray.setValueAtIndexOf("Orange", -1);

        assertArrayEquals(original, myArray.getMyArray());
    }

    @Test
    @DisplayName("SetValueAtIndexOf with out of bounds index does nothing")
    void testSetValueAtIndexOfWithOutOfBoundsIndex() {
        String[] original = myArray.getMyArray().clone();
        myArray.setValueAtIndexOf("Orange", 10);

        assertArrayEquals(original, myArray.getMyArray());
    }

    @Test
    @DisplayName("IsEmpty returns true for empty array")
    void testIsEmptyWithEmptyArray() {
        MyArray emptyArray = MyArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertTrue(emptyArray.isEmpty());
    }

    @Test
    @DisplayName("IsEmpty returns false for non-empty array")
    void testIsEmptyWithNonEmptyArray() {
        assertFalse(myArray.isEmpty());
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with uppercase letters")
    void testCalculateArrayElementAtIndexOfWithUppercase() {
        MyArray array = MyArray.newBuilder()
                .setMyArray(new String[]{"ABC"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        int expected = 'A' + 'B' + 'C';
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with lowercase letters")
    void testCalculateArrayElementAtIndexOfWithLowercase() {
        MyArray array = MyArray.newBuilder()
                .setMyArray(new String[]{"abc"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        int expected = -'a' - 'b' - 'c';
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with mixed case letters")
    void testCalculateArrayElementAtIndexOfWithMixedCase() {
        MyArray array = MyArray.newBuilder()
                .setMyArray(new String[]{"AbC"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        int expected = 'A' - 'b' + 'C';
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf ignores non-letter characters")
    void testCalculateArrayElementAtIndexOfWithNonLetters() {
        MyArray array = MyArray.newBuilder()
                .setMyArray(new String[]{"A1b2C3"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        int expected = 'A' - 'b' + 'C';
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Attach observer successfully")
    void testAttachObserver() {
        myArray.attach(observer);

        myArray.notifyObservers();
        verify(observer, times(1)).handleEvent(myArray);
    }

    @Test
    @DisplayName("Detach observer successfully")
    void testDetachObserver() {
        myArray.attach(observer);
        myArray.detach(observer);

        myArray.notifyObservers();
        verify(observer, never()).handleEvent(myArray);
    }

    @Test
    @DisplayName("Notify observers on setMyArray")
    void testNotifyObserversOnSetMyArray() {
        myArray.attach(observer);
        myArray.setMyArray(new String[]{"X", "Y"});

        verify(observer, times(1)).handleEvent(myArray);
    }

    @Test
    @DisplayName("Notify observers on setValueAtIndexOf")
    void testNotifyObserversOnSetValueAtIndexOf() {
        myArray.attach(observer);
        myArray.setValueAtIndexOf("Orange", 1);

        verify(observer, times(1)).handleEvent(myArray);
    }

    @Test
    @DisplayName("No notification when observer is not attached")
    void testNoNotificationWithoutObserver() {
        myArray.setMyArray(new String[]{"X", "Y"});

        verify(observer, never()).handleEvent(myArray);
    }

    @Test
    @DisplayName("Equals returns true for same object")
    void testEqualsSameObject() {
        assertTrue(myArray.equals(myArray));
    }

    @Test
    @DisplayName("Equals returns true for arrays with same content")
    void testEqualsWithSameContent() {
        MyArray array1 = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();
        MyArray array2 = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();

        assertTrue(array1.equals(array2));
    }

    @Test
    @DisplayName("Equals returns false for arrays with different content")
    void testEqualsWithDifferentContent() {
        MyArray array1 = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();
        MyArray array2 = MyArray.newBuilder()
                .setMyArray(new String[]{"X", "Y", "Z"})
                .build();

        assertFalse(array1.equals(array2));
    }

    @Test
    @DisplayName("Equals returns false for arrays with different lengths")
    void testEqualsWithDifferentLengths() {
        MyArray array1 = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B"})
                .build();
        MyArray array2 = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();

        assertFalse(array1.equals(array2));
    }

    @Test
    @DisplayName("Equals returns false for null")
    void testEqualsWithNull() {
        assertFalse(myArray.equals(null));
    }

    @Test
    @DisplayName("Equals returns false for different class")
    void testEqualsWithDifferentClass() {
        assertFalse(myArray.equals("Not a MyArray"));
    }

    @Test
    @DisplayName("HashCode consistent with equals")
    void testHashCodeConsistentWithEquals() {
        MyArray array1 = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();
        MyArray array2 = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();

        assertEquals(array1.hashCode(), array2.hashCode());
    }

    @Test
    @DisplayName("HashCode for null array")
    void testHashCodeForNullArray() {
        MyArray array = MyArray.newBuilder()
                .setMyArray(null)
                .build();

        assertEquals(0, array.hashCode());
    }

    @Test
    @DisplayName("HashCode for empty array")
    void testHashCodeForEmptyArray() {
        MyArray array = MyArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertEquals(0, array.hashCode());
    }

    @Test
    @DisplayName("ToString contains array and id information")
    void testToString() {
        String result = myArray.toString();

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
        MyArray array = MyArray.newBuilder()
                .setMyArray(new String[]{""})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("CalculateArrayElementAtIndexOf with special characters only")
    void testCalculateArrayElementAtIndexOfWithSpecialCharacters() {
        MyArray array = MyArray.newBuilder()
                .setMyArray(new String[]{"123!@#"})
                .build();

        int result = array.calculateArrayElementAtIndexOf(0);
        assertEquals(0, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("SetMyArray with various invalid inputs returns empty array")
    void testSetMyArrayWithVariousInvalidInputs(String[] invalidArray) {
        myArray.setMyArray(invalidArray);

        assertArrayEquals(new String[0], myArray.getMyArray());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    @DisplayName("SetValueAtIndexOf with various blank values")
    void testSetValueAtIndexOfWithVariousBlankValues(String blankValue) {
        String[] original = myArray.getMyArray().clone();
        myArray.setValueAtIndexOf(blankValue, 1);

        assertArrayEquals(original, myArray.getMyArray());
    }
}
