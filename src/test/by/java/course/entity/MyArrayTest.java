package test.by.java.course.entity;

import main.by.java.course.entity.MyArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayTest {
    private MyArray emptyArray;
    private MyArray singleElementArray;
    private MyArray multiElementArray;
    private MyArray arrayWithNulls;

    @BeforeEach
    void setUp() {
        emptyArray = MyArray.newBuilder()
                .setMyArray(new String[]{})
                .build();

        singleElementArray = MyArray.newBuilder()
                .setMyArray(new String[]{"single"})
                .build();

        multiElementArray = MyArray.newBuilder()
                .setMyArray(new String[]{"apple", "banana", "cherry", "date"})
                .build();

        arrayWithNulls = MyArray.newBuilder()
                .setMyArray(new String[]{"first", null, "third"})
                .build();
    }

    @Test
    @DisplayName("Test getMyArray returns copy, not original array")
    void testGetMyArrayReturnsCopy() {
        String[] original = {"a", "b", "c"};
        MyArray myArray = MyArray.newBuilder()
                .setMyArray(original)
                .build();

        String[] retrieved = myArray.getMyArray();

        assertNotSame(original, retrieved, "getMyArray should return a copy");
        assertArrayEquals(original, retrieved, "Arrays should have same content");

        // Modify original array - should not affect MyArray
        original[0] = "modified";
        assertEquals("a", retrieved[0], "Modifying original should not affect copy");
    }

    @Test
    @DisplayName("Test isEmpty with various arrays")
    void testIsEmpty() {
        assertTrue(emptyArray.isEmpty(), "Empty array should return true for isEmpty");
        assertFalse(singleElementArray.isEmpty(), "Single element array should return false for isEmpty");
        assertFalse(multiElementArray.isEmpty(), "Multi element array should return false for isEmpty");
    }

    @Test
    @DisplayName("Test toString format")
    void testToString() {
        assertEquals("MyArray{array=[]}", emptyArray.toString());
        assertEquals("MyArray{array=[single]}", singleElementArray.toString());
        assertEquals("MyArray{array=[apple, banana, cherry, date]}", multiElementArray.toString());
        assertEquals("MyArray{array=[first, null, third]}", arrayWithNulls.toString());
    }

    @Test
    @DisplayName("Test hashCode consistency with equals")
    void testHashCode() {
        // Same arrays should have same hashCode if equals
        MyArray array1 = MyArray.newBuilder()
                .setMyArray(new String[]{"test"})
                .build();
        MyArray array2 = MyArray.newBuilder()
                .setMyArray(new String[]{"test"})
                .build();

        if (array1.equals(array2)) {
            assertEquals(array1.hashCode(), array2.hashCode(),
                    "Equal objects must have equal hashCodes");
        }

        // Test empty array
        assertEquals(0, emptyArray.hashCode(), "Empty array should have hashCode 0");

        // Test null array (should not happen in practice due to constructor)
        MyArray nullArray = MyArray.newBuilder()
                .setMyArray(null)
                .build();
        assertEquals(0, nullArray.hashCode(), "Array built from null should be empty");
    }

    @Test
    @DisplayName("Test equals with various scenarios")
    void testEquals() {
        MyArray sameMulti = MyArray.newBuilder()
                .setMyArray(new String[]{"apple", "banana", "cherry", "date"})
                .build();

        MyArray differentOrder = MyArray.newBuilder()
                .setMyArray(new String[]{"banana", "apple", "cherry", "date"})
                .build();

        MyArray differentLength = MyArray.newBuilder()
                .setMyArray(new String[]{"apple", "banana", "cherry"})
                .build();

        MyArray differentContent = MyArray.newBuilder()
                .setMyArray(new String[]{"apple", "banana", "cherry", "elderberry"})
                .build();

        // Reflexivity
        assertTrue(multiElementArray.equals(multiElementArray), "Object should equal itself");

        // Symmetry
        assertTrue(multiElementArray.equals(sameMulti), "Arrays with same content should be equal");
        assertTrue(sameMulti.equals(multiElementArray), "Equality should be symmetric");

        // Different arrays
        assertFalse(multiElementArray.equals(differentOrder), "Different order should not be equal");
        assertFalse(multiElementArray.equals(differentLength), "Different length should not be equal");
        assertFalse(multiElementArray.equals(differentContent), "Different content should not be equal");

        // Null and different type
        assertFalse(multiElementArray.equals(null), "Should not equal null");
        assertFalse(multiElementArray.equals("string"), "Should not equal different type");

        // Empty arrays
        MyArray anotherEmpty = MyArray.newBuilder()
                .setMyArray(new String[]{})
                .build();
        assertTrue(emptyArray.equals(anotherEmpty), "Empty arrays should be equal");

        // Arrays with nulls
        MyArray anotherWithNulls = MyArray.newBuilder()
                .setMyArray(new String[]{"first", null, "third"})
                .build();
        assertTrue(arrayWithNulls.equals(anotherWithNulls), "Arrays with same nulls should be equal");
    }

    @Test
    @DisplayName("Test Builder setMyArray with null")
    void testBuilderSetMyArrayWithNull() {
        MyArray.Builder builder = MyArray.newBuilder();
        builder.setMyArray(null);

        MyArray result = builder.build();
        assertTrue(result.isEmpty(), "Building with null array should create empty array");
    }

    @Test
    @DisplayName("Test Builder setMyArray with actual array")
    void testBuilderSetMyArrayWithArray() {
        String[] input = {"x", "y", "z"};
        MyArray result = MyArray.newBuilder()
                .setMyArray(input)
                .build();

        assertArrayEquals(input, result.getMyArray(), "Built array should match input");
        assertNotSame(input, result.getMyArray(), "Built array should be a copy");
    }

    @Test
    @DisplayName("Test Builder build multiple times")
    void testBuilderBuildMultipleTimes() {
        MyArray.Builder builder = MyArray.newBuilder();

        MyArray first = builder.setMyArray(new String[]{"first"}).build();
        MyArray second = builder.setMyArray(new String[]{"second"}).build();

        assertArrayEquals(new String[]{"first"}, first.getMyArray());
        assertArrayEquals(new String[]{"second"}, second.getMyArray());
    }

    @Test
    @DisplayName("Test Builder method chaining")
    void testBuilderMethodChaining() {
        MyArray result = MyArray.newBuilder()
                .setMyArray(new String[]{"a"})
                .setMyArray(new String[]{"b", "c"})
                .build();

        assertArrayEquals(new String[]{"b", "c"}, result.getMyArray(),
                "Last setMyArray should take effect");
    }

    @Test
    @DisplayName("Test newBuilder static method")
    void testNewBuilder() {
        MyArray.Builder builder = MyArray.newBuilder();
        assertNotNull(builder, "newBuilder should return non-null builder");

        MyArray array = builder.setMyArray(new String[]{"test"}).build();
        assertNotNull(array, "Builder should build non-null MyArray");
    }

    @Test
    @DisplayName("Test immutability through getMyArray")
    void testImmutability() {
        String[] original = {"immutable", "test"};
        MyArray myArray = MyArray.newBuilder()
                .setMyArray(original)
                .build();

        String[] retrieved1 = myArray.getMyArray();
        String[] retrieved2 = myArray.getMyArray();

        // Multiple calls should return different copies
        assertNotSame(retrieved1, retrieved2, "Multiple getMyArray calls should return different copies");

        // Modifying retrieved array should not affect MyArray
        retrieved1[0] = "modified";
        String[] retrieved3 = myArray.getMyArray();
        assertEquals("immutable", retrieved3[0], "Modifying retrieved array should not affect MyArray");
    }

    @Test
    @DisplayName("Test edge cases with single element")
    void testSingleElementEdgeCases() {
        assertEquals("single", singleElementArray.getMyArray()[0]);
        assertEquals(1, singleElementArray.getMyArray().length);
        assertFalse(singleElementArray.isEmpty());
    }

    @Test
    @DisplayName("Test equals with arrays containing null elements")
    void testEqualsWithNullElements() {
        MyArray array1 = MyArray.newBuilder()
                .setMyArray(new String[]{null, "value"})
                .build();

        MyArray array2 = MyArray.newBuilder()
                .setMyArray(new String[]{null, "value"})
                .build();

        MyArray array3 = MyArray.newBuilder()
                .setMyArray(new String[]{"value", null})
                .build();

        assertTrue(array1.equals(array2), "Arrays with same null positions should be equal");
        assertFalse(array1.equals(array3), "Arrays with different null positions should not be equal");
    }

    @Test
    @DisplayName("Test performance with large array")
    void testLargeArray() {
        String[] largeArray = new String[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = "element_" + i;
        }

        MyArray myArray = MyArray.newBuilder()
                .setMyArray(largeArray)
                .build();

        assertEquals(1000, myArray.getMyArray().length);
        assertFalse(myArray.isEmpty());
        assertEquals("element_0", myArray.getMyArray()[0]);
        assertEquals("element_999", myArray.getMyArray()[999]);
    }
}