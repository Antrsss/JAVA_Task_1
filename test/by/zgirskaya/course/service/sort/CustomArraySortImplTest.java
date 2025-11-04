package by.zgirskaya.course.service.sort;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.service.sort.impl.CustomArraySortImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomArraySortImplTest {

    private CustomArraySortImpl sorter;
    private CustomArray emptyArray;
    private CustomArray singleElementArray;
    private CustomArray sortedArray;
    private CustomArray reverseSortedArray;
    private CustomArray randomArray;
    private CustomArray arrayWithDuplicates;

    @BeforeEach
    void setUp() {
        sorter = new CustomArraySortImpl();

        emptyArray = CustomArray.newBuilder()
                .setMyArray(new String[]{})
                .build();

        singleElementArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"Apple"})
                .build();

        sortedArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Banana", "Cherry", "Date"})
                .build();

        reverseSortedArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"Date", "Cherry", "Banana", "Apple"})
                .build();

        randomArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"Cherry", "Apple", "Date", "Banana"})
                .build();

        arrayWithDuplicates = CustomArray.newBuilder()
                .setMyArray(new String[]{"Banana", "Apple", "Cherry", "Apple", "Date", "Banana"})
                .build();
    }

    // Selection Sort Tests
    @Test
    @DisplayName("Selection sort with null array throws exception")
    void testSelectionSortWithNullArray() {
        assertThrows(CustomArrayException.class, () -> sorter.selectionSort(null));
    }

    @Test
    @DisplayName("Selection sort with empty array returns same array")
    void testSelectionSortWithEmptyArray() throws CustomArrayException {
        CustomArray result = sorter.selectionSort(emptyArray);

        assertEquals(emptyArray, result);
    }

    @Test
    @DisplayName("Selection sort with single element returns same array")
    void testSelectionSortWithSingleElement() throws CustomArrayException {
        CustomArray result = sorter.selectionSort(singleElementArray);

        assertEquals(singleElementArray, result);
    }

    @Test
    @DisplayName("Selection sort with sorted array returns same array")
    void testSelectionSortWithSortedArray() throws CustomArrayException {
        CustomArray result = sorter.selectionSort(sortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Selection sort with reverse sorted array")
    void testSelectionSortWithReverseSortedArray() throws CustomArrayException {
        CustomArray result = sorter.selectionSort(reverseSortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Selection sort with random array")
    void testSelectionSortWithRandomArray() throws CustomArrayException {
        CustomArray result = sorter.selectionSort(randomArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Selection sort with duplicates")
    void testSelectionSortWithDuplicates() throws CustomArrayException {
        CustomArray result = sorter.selectionSort(arrayWithDuplicates);
        String[] expected = {"Apple", "Apple", "Banana", "Banana", "Cherry", "Date"};

        assertArrayEquals(expected, result.getMyArray());
    }

    // Merge Sort Tests
    @Test
    @DisplayName("Merge sort with null array throws exception")
    void testMergeSortWithNullArray() {
        assertThrows(CustomArrayException.class, () -> sorter.mergeSort(null));
    }

    @Test
    @DisplayName("Merge sort with empty array returns same array")
    void testMergeSortWithEmptyArray() throws CustomArrayException {
        CustomArray result = sorter.mergeSort(emptyArray);

        assertEquals(emptyArray, result);
    }

    @Test
    @DisplayName("Merge sort with single element returns same array")
    void testMergeSortWithSingleElement() throws CustomArrayException {
        CustomArray result = sorter.mergeSort(singleElementArray);

        assertEquals(singleElementArray, result);
    }

    @Test
    @DisplayName("Merge sort with sorted array returns same array")
    void testMergeSortWithSortedArray() throws CustomArrayException {
        CustomArray result = sorter.mergeSort(sortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Merge sort with reverse sorted array")
    void testMergeSortWithReverseSortedArray() throws CustomArrayException {
        CustomArray result = sorter.mergeSort(reverseSortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Merge sort with random array")
    void testMergeSortWithRandomArray() throws CustomArrayException {
        CustomArray result = sorter.mergeSort(randomArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Merge sort with duplicates")
    void testMergeSortWithDuplicates() throws CustomArrayException {
        CustomArray result = sorter.mergeSort(arrayWithDuplicates);
        String[] expected = {"Apple", "Apple", "Banana", "Banana", "Cherry", "Date"};

        assertArrayEquals(expected, result.getMyArray());
    }

    // Quick Sort Tests
    @Test
    @DisplayName("Quick sort with null array throws exception")
    void testQuickSortWithNullArray() {
        assertThrows(CustomArrayException.class, () -> sorter.quickSort(null));
    }

    @Test
    @DisplayName("Quick sort with empty array returns same array")
    void testQuickSortWithEmptyArray() throws CustomArrayException {
        CustomArray result = sorter.quickSort(emptyArray);

        assertEquals(emptyArray, result);
    }

    @Test
    @DisplayName("Quick sort with single element returns same array")
    void testQuickSortWithSingleElement() throws CustomArrayException {
        CustomArray result = sorter.quickSort(singleElementArray);

        assertEquals(singleElementArray, result);
    }

    @Test
    @DisplayName("Quick sort with sorted array returns same array")
    void testQuickSortWithSortedArray() throws CustomArrayException {
        CustomArray result = sorter.quickSort(sortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Quick sort with reverse sorted array")
    void testQuickSortWithReverseSortedArray() throws CustomArrayException {
        CustomArray result = sorter.quickSort(reverseSortedArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Quick sort with random array")
    void testQuickSortWithRandomArray() throws CustomArrayException {
        CustomArray result = sorter.quickSort(randomArray);

        assertArrayEquals(sortedArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Quick sort with duplicates")
    void testQuickSortWithDuplicates() throws CustomArrayException {
        CustomArray result = sorter.quickSort(arrayWithDuplicates);
        String[] expected = {"Apple", "Apple", "Banana", "Banana", "Cherry", "Date"};

        assertArrayEquals(expected, result.getMyArray());
    }

    // Comparative Tests
    @Test
    @DisplayName("All sort algorithms produce same result")
    void testAllSortsProduceSameResult() throws CustomArrayException {
        CustomArray selectionResult = sorter.selectionSort(randomArray);
        CustomArray mergeResult = sorter.mergeSort(randomArray);
        CustomArray quickResult = sorter.quickSort(randomArray);

        assertArrayEquals(selectionResult.getMyArray(), mergeResult.getMyArray());
        assertArrayEquals(mergeResult.getMyArray(), quickResult.getMyArray());
        assertArrayEquals(quickResult.getMyArray(), selectionResult.getMyArray());
    }

    @Test
    @DisplayName("Sort algorithms preserve array size")
    void testSortAlgorithmsPreserveSize() throws CustomArrayException {
        CustomArray selectionResult = sorter.selectionSort(arrayWithDuplicates);
        CustomArray mergeResult = sorter.mergeSort(arrayWithDuplicates);
        CustomArray quickResult = sorter.quickSort(arrayWithDuplicates);

        assertEquals(arrayWithDuplicates.getMyArray().length, selectionResult.getMyArray().length);
        assertEquals(arrayWithDuplicates.getMyArray().length, mergeResult.getMyArray().length);
        assertEquals(arrayWithDuplicates.getMyArray().length, quickResult.getMyArray().length);
    }

    // Edge Cases
    @Test
    @DisplayName("Sort with all same elements")
    void testSortWithAllSameElements() throws CustomArrayException {
        CustomArray sameElementsArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Apple", "Apple", "Apple"})
                .build();

        CustomArray result = sorter.selectionSort(sameElementsArray);
        assertArrayEquals(sameElementsArray.getMyArray(), result.getMyArray());
    }

    @Test
    @DisplayName("Sort with case sensitivity")
    void testSortWithCaseSensitivity() throws CustomArrayException {
        CustomArray mixedCaseArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"apple", "Banana", "cherry", "Date"})
                .build();

        CustomArray result = sorter.selectionSort(mixedCaseArray);

        String[] expected = {"Banana", "Date", "apple", "cherry"};
        assertArrayEquals(expected, result.getMyArray());
    }

    @Test
    @DisplayName("Original array is not modified")
    void testOriginalArrayNotModified() throws CustomArrayException {
        String[] original = randomArray.getMyArray().clone();
        sorter.selectionSort(randomArray);

        assertArrayEquals(original, randomArray.getMyArray());
    }
}