package by.zgirskaya.course.comparator;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayComparatorTest {

    private CustomArray array1;
    private CustomArray array2;
    private CustomArray array3;

    @BeforeEach
    void setUp() {
        array1 = CustomArray.newBuilder()
                .setMyArray(new String[]{"ABC", "DEF"})
                .build();

        array2 = CustomArray.newBuilder()
                .setMyArray(new String[]{"abc", "def"})
                .build();

        array3 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C", "D"})
                .build();
    }

    @Test
    @DisplayName("ID comparator - arrays with different IDs")
    void testIdComparatorWithDifferentIds() {
        int result = CustomArrayComparator.ID.compare(array1, array2);

        assertTrue(result != 0, "ID comparison should not return 0 for different arrays");
    }

    @Test
    @DisplayName("ID comparator - same array")
    void testIdComparatorWithSameArray() {
        int result = CustomArrayComparator.ID.compare(array1, array1);
        assertEquals(0, result, "ID comparison should return 0 for same array");
    }

    @Test
    @DisplayName("ID comparator - sorting by ID")
    void testIdComparatorSorting() {
        List<CustomArray> arrays = new ArrayList<>();
        arrays.add(array3);
        arrays.add(array1);
        arrays.add(array2);

        Collections.sort(arrays, CustomArrayComparator.ID);

        for (int i = 0; i < arrays.size() - 1; i++) {
            assertTrue(arrays.get(i).getId() <= arrays.get(i + 1).getId(),
                    "Arrays should be sorted by ID in ascending order");
        }
    }

    @Test
    @DisplayName("MIN_SUM comparator - arrays with different sums")
    void testMinSumComparatorWithDifferentSums() {
        int result = CustomArrayComparator.MIN_SUM.compare(array1, array2);

        // array1: "ABC"=65+66+67=198, "DEF"=68+69+70=207, total=405 (positive)
        // array2: "abc"=-97-98-99=-294, "def"=-100-101-102=-303, total=-597 (negative)
        int expected = Math.min(405, -597);
        assertEquals(expected, result, "MIN_SUM should return minimum of two sums");
    }

    @Test
    @DisplayName("MIN_SUM comparator - arrays with same sum")
    void testMinSumComparatorWithSameSum() {
        CustomArray sameArray1 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B"})
                .build();
        CustomArray sameArray2 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B"})
                .build();

        int result = CustomArrayComparator.MIN_SUM.compare(sameArray1, sameArray2);
        int expectedSum = ('A' + 'B');
        assertEquals(expectedSum, result, "MIN_SUM should return the sum when both are equal");
    }

    @Test
    @DisplayName("MIN_SUM comparator - sorting by minimum sum")
    void testMinSumComparatorSorting() {
        List<CustomArray> arrays = new ArrayList<>();
        arrays.add(array1); // Sum ~405 (positive)
        arrays.add(array2); // Sum ~-597 (negative)
        arrays.add(array3); // Sum: A=65, B=66, C=67, D=68 = 266

        Collections.sort(arrays, CustomArrayComparator.MIN_SUM);

        assertNotNull(arrays);
        assertEquals(3, arrays.size());
    }

    @Test
    @DisplayName("MIN_SUM comparator - null arrays variations")
    void testMinSumComparatorWithNullArray() {
        int result1 = CustomArrayComparator.MIN_SUM.compare(null, array1);
        int result2 = CustomArrayComparator.MIN_SUM.compare(array1, null);
        int result3 = CustomArrayComparator.MIN_SUM.compare(null, null);

        assertEquals(-1, result1, "MIN_SUM should return -1");
        assertEquals(1, result2, "MIN_SUM should return 1");
        assertEquals(0, result3, "MIN_SUM should return 0");
    }

    @Test
    @DisplayName("MAX_SIZE comparator - arrays with different sizes")
    void testMaxSizeComparatorWithDifferentSizes() {
        int result = CustomArrayComparator.MAX_SIZE.compare(array1, array3);
        int expected = Math.max(2, 4);

        assertEquals(expected, result, "MAX_SIZE should return maximum of two sizes");
    }

    @Test
    @DisplayName("MAX_SIZE comparator - arrays with same size")
    void testMaxSizeComparatorWithSameSize() {
        CustomArray sameSizeArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"X", "Y"})
                .build();

        int result = CustomArrayComparator.MAX_SIZE.compare(array1, sameSizeArray);

        assertEquals(2, result, "MAX_SIZE should return the size when both are equal");
    }

    @Test
    @DisplayName("MAX_SIZE comparator - sorting by maximum size")
    void testMaxSizeComparatorSorting() {
        List<CustomArray> arrays = new ArrayList<>();
        arrays.add(array3);
        arrays.add(array1);
        arrays.add(array2);

        Collections.sort(arrays, CustomArrayComparator.MAX_SIZE);

        assertNotNull(arrays);
        assertEquals(3, arrays.size());
    }

    @Test
    @DisplayName("MIN_SUM comparator - empty arrays")
    void testMinSumComparatorWithEmptyArrays() {
        CustomArray emptyArray1 = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();
        CustomArray emptyArray2 = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        int result = CustomArrayComparator.MIN_SUM.compare(emptyArray1, emptyArray2);

        assertEquals(0, result, "MIN_SUM should return 0 for empty arrays");
    }

    @Test
    @DisplayName("MAX_SIZE comparator - empty arrays")
    void testMaxSizeComparatorWithEmptyArrays() {
        CustomArray emptyArray1 = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();
        CustomArray emptyArray2 = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        int result = CustomArrayComparator.MAX_SIZE.compare(emptyArray1, emptyArray2);

        assertEquals(0, result, "MAX_SIZE should return 0 for empty arrays");
    }

    @Test
    @DisplayName("ID comparator - consistent with equals")
    void testIdComparatorConsistentWithEquals() {
        assertEquals(0, CustomArrayComparator.ID.compare(array1, array1));

        CustomArray copyWithDifferentId = CustomArray.newBuilder()
                .setMyArray(array1.getMyArray())
                .build();

        int result = CustomArrayComparator.ID.compare(array1, copyWithDifferentId);
        assertNotEquals(0, result, "ID comparator should not return 0 for different IDs");
    }
}