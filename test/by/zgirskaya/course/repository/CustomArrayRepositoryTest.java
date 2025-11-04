package by.zgirskaya.course.repository;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.specification.CustomArraySpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayRepositoryTest {

    private CustomArrayRepository repository;
    private CustomArray array1;
    private CustomArray array2;
    private CustomArray array3;

    @BeforeEach
    void setUp() {
        repository = new CustomArrayRepository();

        array1 = CustomArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Banana"})
                .build();

        array2 = CustomArray.newBuilder()
                .setMyArray(new String[]{"Cherry", "Date", "Elderberry"})
                .build();

        array3 = CustomArray.newBuilder()
                .setMyArray(new String[]{"Fig"})
                .build();
    }

    @Test
    @DisplayName("AddMyArray actually adds MyArray to repository")
    void testAddMyArrayActuallyAddsToRepository() {
        repository.customArrays.add(array1);
        repository.customArrays.add(array2);

        List<CustomArray> allArrays = repository.query(new AllSpecification());
        assertEquals(2, allArrays.size());
        assertTrue(allArrays.contains(array1));
        assertTrue(allArrays.contains(array2));
    }

    @Test
    @DisplayName("AddMyArray allows duplicate MyArrays")
    void testAddMyArrayAllowsDuplicates() {
        repository.customArrays.add(array1);
        repository.customArrays.add(array1);

        List<CustomArray> allArrays = repository.query(new AllSpecification());
        assertEquals(2, allArrays.size());
        assertEquals(2, allArrays.stream().filter(a -> a.equals(array1)).count());
    }

    @Test
    @DisplayName("RemoveMyArray with existing MyArray returns true")
    void testRemoveMyArrayWithExistingMyArray() {
        repository.customArrays.add(array1);
        repository.customArrays.add(array2);

        boolean result = repository.customArrays.remove(array1);

        assertTrue(result);

        List<CustomArray> remaining = repository.query(new AllSpecification());
        assertEquals(1, remaining.size());
        assertFalse(remaining.contains(array1));
        assertTrue(remaining.contains(array2));
    }

    @Test
    @DisplayName("RemoveMyArray with non-existing MyArray returns false")
    void testRemoveMyArrayWithNonExistingMyArray() {
        repository.customArrays.add(array1);

        boolean result = repository.customArrays.remove(array2);

        assertFalse(result);

        List<CustomArray> remaining = repository.query(new AllSpecification());
        assertEquals(1, remaining.size());
        assertTrue(remaining.contains(array1));
    }

    @Test
    @DisplayName("RemoveMyArray with null returns false")
    void testRemoveMyArrayWithNull() {
        repository.customArrays.add(array1);

        boolean result = repository.customArrays.remove(null);

        assertFalse(result);

        List<CustomArray> remaining = repository.query(new AllSpecification());
        assertEquals(1, remaining.size());
        assertTrue(remaining.contains(array1));
    }

    @Test
    @DisplayName("RemoveMyArray removes only one occurrence")
    void testRemoveMyArrayRemovesOnlyOneOccurrence() {
        repository.customArrays.add(array1);
        repository.customArrays.add(array1);
        repository.customArrays.add(array2);

        boolean result = repository.customArrays.remove(array1);

        assertTrue(result);

        List<CustomArray> remaining = repository.query(new AllSpecification());
        assertEquals(2, remaining.size());
        assertEquals(1, remaining.stream().filter(a -> a.equals(array1)).count());
        assertTrue(remaining.contains(array2));
    }

    @Test
    @DisplayName("Sort with valid comparator returns sorted list")
    void testSortWithValidComparator() {
        repository.customArrays.add(array3);
        repository.customArrays.add(array1);
        repository.customArrays.add(array2);

        Comparator<CustomArray> sizeComparator = Comparator.comparing(arr -> arr.getMyArray().length);

        List<CustomArray> sorted = repository.sort(sizeComparator);

        assertEquals(3, sorted.size());
        assertEquals(array3, sorted.get(0));
        assertEquals(array1, sorted.get(1));
        assertEquals(array2, sorted.get(2));
    }

    @Test
    @DisplayName("Sort does not modify original repository")
    void testSortDoesNotModifyOriginalRepository() {
        repository.customArrays.add(array1);
        repository.customArrays.add(array2);

        List<CustomArray> beforeSort = repository.query(new AllSpecification());
        repository.sort(Comparator.comparing(CustomArray::getId));
        List<CustomArray> afterSort = repository.query(new AllSpecification());

        assertEquals(beforeSort, afterSort);
    }

    @Test
    @DisplayName("Sort returns new list instance")
    void testSortReturnsNewListInstance() {
        repository.customArrays.add(array1);

        List<CustomArray> sort1 = repository.sort(Comparator.comparing(CustomArray::getId));
        List<CustomArray> sort2 = repository.sort(Comparator.comparing(CustomArray::getId));

        assertNotSame(sort1, sort2);
        assertEquals(sort1, sort2);
    }

    @Test
    @DisplayName("Query with valid specification returns matching arrays")
    void testQueryWithValidSpecification() {
        repository.customArrays.add(array1);
        repository.customArrays.add(array2);
        repository.customArrays.add(array3);

        CustomArraySpecification sizeSpecification = new SizeGreaterThanSpecification(1);

        List<CustomArray> result = repository.query(sizeSpecification);

        assertEquals(2, result.size());
        assertTrue(result.contains(array1));
        assertTrue(result.contains(array2));
        assertFalse(result.contains(array3));
    }

    @Test
    @DisplayName("Query with specification that matches nothing returns empty list")
    void testQueryWithNoMatchesReturnsEmptyList() {
        repository.customArrays.add(array1);
        repository.customArrays.add(array2);

        CustomArraySpecification neverMatchSpecification = array -> false;

        List<CustomArray> result = repository.query(neverMatchSpecification);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Query returns immutable list")
    void testQueryReturnsImmutableList() {
        repository.customArrays.add(array1);

        List<CustomArray> result = repository.query(new AllSpecification());

        assertThrows(Exception.class, () -> result.add(array2));
        assertThrows(Exception.class, () -> result.remove(0));
        assertThrows(Exception.class, () -> result.clear());
    }

    @Test
    @DisplayName("Query returns new list instance each time")
    void testQueryReturnsNewListInstance() {
        repository.customArrays.add(array1);

        List<CustomArray> query1 = repository.query(new AllSpecification());
        List<CustomArray> query2 = repository.query(new AllSpecification());

        assertNotSame(query1, query2);
        assertEquals(query1, query2);
    }

    @Test
    @DisplayName("Empty repository behavior")
    void testEmptyRepositoryBehavior() {
        List<CustomArray> queryResult = repository.query(new AllSpecification());
        assertTrue(queryResult.isEmpty());

        List<CustomArray> sortResult = repository.sort(Comparator.comparing(CustomArray::getId));
        assertTrue(sortResult.isEmpty());

        boolean removeResult = repository.customArrays.remove(array1);
        assertFalse(removeResult);
    }

    @Test
    @DisplayName("Query with complex specification")
    void testQueryWithComplexSpecification() {
        CustomArray arrayWithA = CustomArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Ant"})
                .build();
        CustomArray arrayWithB = CustomArray.newBuilder()
                .setMyArray(new String[]{"Banana", "Berry"})
                .build();
        CustomArray arrayWithC = CustomArray.newBuilder()
                .setMyArray(new String[]{"Cherry", "Currant"})
                .build();

        repository.customArrays.add(arrayWithA);
        repository.customArrays.add(arrayWithB);
        repository.customArrays.add(arrayWithC);

        CustomArraySpecification startsWithASpecification = array -> {
            String[] elements = array.getMyArray();
            for (String element : elements) {
                if (element.startsWith("A")) {
                    return true;
                }
            }
            return false;
        };

        List<CustomArray> result = repository.query(startsWithASpecification);

        assertEquals(1, result.size());
        assertEquals(arrayWithA, result.get(0));
    }

    private static class AllSpecification implements CustomArraySpecification {
        @Override
        public boolean specify(CustomArray customArray) {
            return true;
        }
    }

    private static class SizeGreaterThanSpecification implements CustomArraySpecification {
        private final int minSize;

        public SizeGreaterThanSpecification(int minSize) {
            this.minSize = minSize;
        }

        @Override
        public boolean specify(CustomArray customArray) {
            return customArray.getMyArray().length > minSize;
        }
    }

    private static class NeverMatchSpecification implements CustomArraySpecification {
        @Override
        public boolean specify(CustomArray customArray) {
            return false;
        }
    }
}