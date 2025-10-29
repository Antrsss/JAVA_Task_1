package test.by.java.course.service.sort;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.service.sort.impl.StreamMyArraySortImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StreamMyArraySortImplTest {

    private StreamMyArraySortImpl sorter;
    private MyArray emptyArray;
    private MyArray singleElementArray;
    private MyArray sortedArray;
    private MyArray reverseSortedArray;
    private MyArray randomArray;
    private MyArray arrayWithDuplicates;

    @BeforeEach
    void setUp() {
        sorter = new StreamMyArraySortImpl();

        emptyArray = MyArray.newBuilder()
                .setMyArray(new String[]{})
                .build();

        singleElementArray = MyArray.newBuilder()
                .setMyArray(new String[]{"Apple"})
                .build();

        sortedArray = MyArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Banana", "Cherry", "Date"})
                .build();

        reverseSortedArray = MyArray.newBuilder()
                .setMyArray(new String[]{"Date", "Cherry", "Banana", "Apple"})
                .build();

        randomArray = MyArray.newBuilder()
                .setMyArray(new String[]{"Cherry", "Apple", "Date", "Banana"})
                .build();

        arrayWithDuplicates = MyArray.newBuilder()
                .setMyArray(new String[]{"Banana", "Apple", "Cherry", "Apple", "Date", "Banana"})
                .build();
    }

    // Selection Sort Tests
    @Test
    @DisplayName("Selection sort with null array throws exception")
    void testSelectionSortWithNullArray() {
        assertThrows(MyArrayException.class, () -> sorter.selectionSort(null));
    }

    @Test
    @DisplayName("Selection sort with empty array returns same array")
    void testSelectionSortWithEmptyArray() throws MyArrayException {
        MyArray result = sorter.selectionSort(emptyArray);

        assertEquals(emptyArray, result);
    }

    @Test
    @DisplayName("Selection sort with single element returns same array")
    void testSelectionSortWithSingleElement() throws MyArrayException {
        MyArray result = sorter.selectionSort(singleElementArray);

        assertEquals(singleElementArray, result);
    }

    @Test
    @DisplayName("Selection sort with sorted array returns same array")
    void testSelectionSortWithSortedArray() throws MyArrayException {
        MyArray result = sorter.selectionSort(sortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Selection sort with reverse sorted array")
    void testSelectionSortWithReverseSortedArray() throws MyArrayException {
        MyArray result = sorter.selectionSort(reverseSortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Selection sort with random array")
    void testSelectionSortWithRandomArray() throws MyArrayException {
        MyArray result = sorter.selectionSort(randomArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Selection sort with duplicates")
    void testSelectionSortWithDuplicates() throws MyArrayException {
        MyArray result = sorter.selectionSort(arrayWithDuplicates);
        String[] expected = {"Apple", "Apple", "Banana", "Banana", "Cherry", "Date"};

        assertArrayEquals(expected, result.getMyArray());
    }

    // Merge Sort Tests
    @Test
    @DisplayName("Merge sort with null array throws exception")
    void testMergeSortWithNullArray() {
        assertThrows(MyArrayException.class, () -> sorter.mergeSort(null));
    }

    @Test
    @DisplayName("Merge sort with empty array returns same array")
    void testMergeSortWithEmptyArray() throws MyArrayException {
        MyArray result = sorter.mergeSort(emptyArray);

        assertEquals(emptyArray, result);
    }

    @Test
    @DisplayName("Merge sort with single element returns same array")
    void testMergeSortWithSingleElement() throws MyArrayException {
        MyArray result = sorter.mergeSort(singleElementArray);

        assertEquals(singleElementArray, result);
    }

    @Test
    @DisplayName("Merge sort with sorted array returns same array")
    void testMergeSortWithSortedArray() throws MyArrayException {
        MyArray result = sorter.mergeSort(sortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Merge sort with reverse sorted array")
    void testMergeSortWithReverseSortedArray() throws MyArrayException {
        MyArray result = sorter.mergeSort(reverseSortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Merge sort with random array")
    void testMergeSortWithRandomArray() throws MyArrayException {
        MyArray result = sorter.mergeSort(randomArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Merge sort with duplicates")
    void testMergeSortWithDuplicates() throws MyArrayException {
        MyArray result = sorter.mergeSort(arrayWithDuplicates);
        String[] expected = {"Apple", "Apple", "Banana", "Banana", "Cherry", "Date"};

        assertArrayEquals(expected, result.getMyArray());
    }

    // Quick Sort Tests
    @Test
    @DisplayName("Quick sort with null array throws exception")
    void testQuickSortWithNullArray() {
        assertThrows(MyArrayException.class, () -> sorter.quickSort(null));
    }

    @Test
    @DisplayName("Quick sort with empty array returns same array")
    void testQuickSortWithEmptyArray() throws MyArrayException {
        MyArray result = sorter.quickSort(emptyArray);

        assertEquals(emptyArray, result);
    }

    @Test
    @DisplayName("Quick sort with single element returns same array")
    void testQuickSortWithSingleElement() throws MyArrayException {
        MyArray result = sorter.quickSort(singleElementArray);

        assertEquals(singleElementArray, result);
    }

    @Test
    @DisplayName("Quick sort with sorted array returns same array")
    void testQuickSortWithSortedArray() throws MyArrayException {
        MyArray result = sorter.quickSort(sortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Quick sort with reverse sorted array")
    void testQuickSortWithReverseSortedArray() throws MyArrayException {
        MyArray result = sorter.quickSort(reverseSortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Quick sort with random array")
    void testQuickSortWithRandomArray() throws MyArrayException {
        MyArray result = sorter.quickSort(randomArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Quick sort with duplicates")
    void testQuickSortWithDuplicates() throws MyArrayException {
        MyArray result = sorter.quickSort(arrayWithDuplicates);
        String[] expected = {"Apple", "Apple", "Banana", "Banana", "Cherry", "Date"};

        assertArrayEquals(expected, result.getMyArray());
    }

    // Comparative Tests
    @Test
    @DisplayName("All sort algorithms produce same result")
    void testAllSortsProduceSameResult() throws MyArrayException {
        MyArray selectionResult = sorter.selectionSort(randomArray);
        MyArray mergeResult = sorter.mergeSort(randomArray);
        MyArray quickResult = sorter.quickSort(randomArray);

        assertArrayEquals(selectionResult.getMyArray(), mergeResult.getMyArray());
        assertArrayEquals(mergeResult.getMyArray(), quickResult.getMyArray());
        assertArrayEquals(quickResult.getMyArray(), selectionResult.getMyArray());
    }

    @Test
    @DisplayName("Sort algorithms preserve array size")
    void testSortAlgorithmsPreserveSize() throws MyArrayException {
        MyArray selectionResult = sorter.selectionSort(arrayWithDuplicates);
        MyArray mergeResult = sorter.mergeSort(arrayWithDuplicates);
        MyArray quickResult = sorter.quickSort(arrayWithDuplicates);

        assertEquals(arrayWithDuplicates.getMyArray().length, selectionResult.getMyArray().length);
        assertEquals(arrayWithDuplicates.getMyArray().length, mergeResult.getMyArray().length);
        assertEquals(arrayWithDuplicates.getMyArray().length, quickResult.getMyArray().length);
    }

    // Edge Cases
    @Test
    @DisplayName("Sort with all same elements")
    void testSortWithAllSameElements() throws MyArrayException {
        MyArray sameElementsArray = MyArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Apple", "Apple", "Apple"})
                .build();

        MyArray result = sorter.selectionSort(sameElementsArray);
        assertArrayEquals(sameElementsArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Sort with case sensitivity")
    void testSortWithCaseSensitivity() throws MyArrayException {
        MyArray mixedCaseArray = MyArray.newBuilder()
                .setMyArray(new String[]{"apple", "Banana", "cherry", "Date"})
                .build();

        MyArray result = sorter.selectionSort(mixedCaseArray);

        String[] expected = {"Banana", "Date", "apple", "cherry"};
        assertArrayEquals(expected, result.getMyArray());
    }

    @Test
    @DisplayName("Original array is not modified")
    void testOriginalArrayNotModified() throws MyArrayException {
        String[] original = randomArray.getMyArray().clone();
        sorter.selectionSort(randomArray);

        assertArrayEquals(original, randomArray.getMyArray());
    }
}