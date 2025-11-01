package test.by.java.course.repository;

import main.by.java.course.entity.MyArray;
import main.by.java.course.repository.impl.MyArrayRepositoryImpl;
import main.by.java.course.specification.MyArraySpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayRepositoryImplTest {

    private MyArrayRepositoryImpl repository;
    private MyArray array1;
    private MyArray array2;
    private MyArray array3;

    @BeforeEach
    void setUp() {
        repository = new MyArrayRepositoryImpl();

        array1 = MyArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Banana"})
                .build();

        array2 = MyArray.newBuilder()
                .setMyArray(new String[]{"Cherry", "Date", "Elderberry"})
                .build();

        array3 = MyArray.newBuilder()
                .setMyArray(new String[]{"Fig"})
                .build();
    }

    @Test
    @DisplayName("AddMyArray with valid MyArray returns true")
    void testAddMyArrayWithValidMyArray() {
        boolean result = repository.addMyArray(array1);

        assertTrue(result);
    }

    @Test
    @DisplayName("AddMyArray with null MyArray returns true")
    void testAddMyArrayWithNullMyArray() {
        boolean result = repository.addMyArray(null);

        assertTrue(result);
    }

    @Test
    @DisplayName("AddMyArray actually adds MyArray to repository")
    void testAddMyArrayActuallyAddsToRepository() {
        repository.addMyArray(array1);
        repository.addMyArray(array2);

        List<MyArray> allArrays = repository.query(new AllSpecification());
        assertEquals(2, allArrays.size());
        assertTrue(allArrays.contains(array1));
        assertTrue(allArrays.contains(array2));
    }

    @Test
    @DisplayName("AddMyArray allows duplicate MyArrays")
    void testAddMyArrayAllowsDuplicates() {
        repository.addMyArray(array1);
        repository.addMyArray(array1);

        List<MyArray> allArrays = repository.query(new AllSpecification());
        assertEquals(2, allArrays.size());
        assertEquals(2, allArrays.stream().filter(a -> a.equals(array1)).count());
    }

    @Test
    @DisplayName("RemoveMyArray with existing MyArray returns true")
    void testRemoveMyArrayWithExistingMyArray() {
        repository.addMyArray(array1);
        repository.addMyArray(array2);

        boolean result = repository.removeMyArray(array1);

        assertTrue(result);

        List<MyArray> remaining = repository.query(new AllSpecification());
        assertEquals(1, remaining.size());
        assertFalse(remaining.contains(array1));
        assertTrue(remaining.contains(array2));
    }

    @Test
    @DisplayName("RemoveMyArray with non-existing MyArray returns false")
    void testRemoveMyArrayWithNonExistingMyArray() {
        repository.addMyArray(array1);

        boolean result = repository.removeMyArray(array2);

        assertFalse(result);

        List<MyArray> remaining = repository.query(new AllSpecification());
        assertEquals(1, remaining.size());
        assertTrue(remaining.contains(array1));
    }

    @Test
    @DisplayName("RemoveMyArray with null returns false")
    void testRemoveMyArrayWithNull() {
        repository.addMyArray(array1);

        boolean result = repository.removeMyArray(null);

        assertFalse(result);

        List<MyArray> remaining = repository.query(new AllSpecification());
        assertEquals(1, remaining.size());
        assertTrue(remaining.contains(array1));
    }

    @Test
    @DisplayName("RemoveMyArray removes only one occurrence")
    void testRemoveMyArrayRemovesOnlyOneOccurrence() {
        repository.addMyArray(array1);
        repository.addMyArray(array1);
        repository.addMyArray(array2);

        boolean result = repository.removeMyArray(array1);

        assertTrue(result);

        List<MyArray> remaining = repository.query(new AllSpecification());
        assertEquals(2, remaining.size());
        assertEquals(1, remaining.stream().filter(a -> a.equals(array1)).count());
        assertTrue(remaining.contains(array2));
    }

    @Test
    @DisplayName("Sort with valid comparator returns sorted list")
    void testSortWithValidComparator() {
        repository.addMyArray(array3);
        repository.addMyArray(array1);
        repository.addMyArray(array2);

        Comparator<MyArray> sizeComparator = Comparator.comparing(arr -> arr.getMyArray().length);

        List<MyArray> sorted = repository.sort(sizeComparator);

        assertEquals(3, sorted.size());
        assertEquals(array3, sorted.get(0));
        assertEquals(array1, sorted.get(1));
        assertEquals(array2, sorted.get(2));
    }

    @Test
    @DisplayName("Sort with null comparator returns copy of original list")
    void testSortWithNullComparator() {
        repository.addMyArray(array1);
        repository.addMyArray(array2);

        List<MyArray> original = repository.query(new AllSpecification());
        List<MyArray> result = repository.sort(null);

        assertNotSame(original, result);
        assertEquals(original, result);
    }

    @Test
    @DisplayName("Sort does not modify original repository")
    void testSortDoesNotModifyOriginalRepository() {
        repository.addMyArray(array1);
        repository.addMyArray(array2);

        List<MyArray> beforeSort = repository.query(new AllSpecification());
        repository.sort(Comparator.comparing(MyArray::getId));
        List<MyArray> afterSort = repository.query(new AllSpecification());

        assertEquals(beforeSort, afterSort);
    }

    @Test
    @DisplayName("Sort returns new list instance")
    void testSortReturnsNewListInstance() {
        repository.addMyArray(array1);

        List<MyArray> sort1 = repository.sort(Comparator.comparing(MyArray::getId));
        List<MyArray> sort2 = repository.sort(Comparator.comparing(MyArray::getId));

        assertNotSame(sort1, sort2);
        assertEquals(sort1, sort2);
    }

    @Test
    @DisplayName("Query with valid specification returns matching arrays")
    void testQueryWithValidSpecification() {
        repository.addMyArray(array1);
        repository.addMyArray(array2);
        repository.addMyArray(array3);

        MyArraySpecification sizeSpecification = new SizeGreaterThanSpecification(1);

        List<MyArray> result = repository.query(sizeSpecification);

        assertEquals(2, result.size());
        assertTrue(result.contains(array1));
        assertTrue(result.contains(array2));
        assertFalse(result.contains(array3));
    }

    @Test
    @DisplayName("Query with null specification returns all arrays")
    void testQueryWithNullSpecification() {
        repository.addMyArray(array1);
        repository.addMyArray(array2);

        List<MyArray> result = repository.query(null);

        assertEquals(2, result.size());
        assertTrue(result.contains(array1));
        assertTrue(result.contains(array2));
    }

    @Test
    @DisplayName("Query with specification that matches nothing returns empty list")
    void testQueryWithNoMatchesReturnsEmptyList() {
        repository.addMyArray(array1);
        repository.addMyArray(array2);

        MyArraySpecification neverMatchSpecification = array -> false;

        List<MyArray> result = repository.query(neverMatchSpecification);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Query returns immutable list")
    void testQueryReturnsImmutableList() {
        repository.addMyArray(array1);

        List<MyArray> result = repository.query(new AllSpecification());

        assertThrows(Exception.class, () -> result.add(array2));
        assertThrows(Exception.class, () -> result.remove(0));
        assertThrows(Exception.class, () -> result.clear());
    }

    @Test
    @DisplayName("Query returns new list instance each time")
    void testQueryReturnsNewListInstance() {
        repository.addMyArray(array1);

        List<MyArray> query1 = repository.query(new AllSpecification());
        List<MyArray> query2 = repository.query(new AllSpecification());

        assertNotSame(query1, query2);
        assertEquals(query1, query2);
    }

    @Test
    @DisplayName("Empty repository behavior")
    void testEmptyRepositoryBehavior() {
        List<MyArray> queryResult = repository.query(new AllSpecification());
        assertTrue(queryResult.isEmpty());

        List<MyArray> sortResult = repository.sort(Comparator.comparing(MyArray::getId));
        assertTrue(sortResult.isEmpty());

        boolean removeResult = repository.removeMyArray(array1);
        assertFalse(removeResult);
    }

    @Test
    @DisplayName("Add and remove same array multiple times")
    void testAddAndRemoveSameArrayMultipleTimes() {
        repository.addMyArray(array1);
        repository.addMyArray(array1);

        assertEquals(2, repository.query(new AllSpecification()).size());

        repository.removeMyArray(array1);
        assertEquals(1, repository.query(new AllSpecification()).size());

        repository.removeMyArray(array1);
        assertEquals(0, repository.query(new AllSpecification()).size());

        repository.removeMyArray(array1); // Удаление несуществующего
        assertEquals(0, repository.query(new AllSpecification()).size());
    }

    @Test
    @DisplayName("Query with complex specification")
    void testQueryWithComplexSpecification() {
        MyArray arrayWithA = MyArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Ant"})
                .build();
        MyArray arrayWithB = MyArray.newBuilder()
                .setMyArray(new String[]{"Banana", "Berry"})
                .build();
        MyArray arrayWithC = MyArray.newBuilder()
                .setMyArray(new String[]{"Cherry", "Currant"})
                .build();

        repository.addMyArray(arrayWithA);
        repository.addMyArray(arrayWithB);
        repository.addMyArray(arrayWithC);

        MyArraySpecification startsWithASpecification = array -> {
            String[] elements = array.getMyArray();
            for (String element : elements) {
                if (element.startsWith("A")) {
                    return true;
                }
            }
            return false;
        };

        List<MyArray> result = repository.query(startsWithASpecification);

        assertEquals(1, result.size());
        assertEquals(arrayWithA, result.get(0));
    }

    private static class AllSpecification implements MyArraySpecification {
        @Override
        public boolean specify(MyArray myArray) {
            return true;
        }
    }

    private static class SizeGreaterThanSpecification implements MyArraySpecification {
        private final int minSize;

        public SizeGreaterThanSpecification(int minSize) {
            this.minSize = minSize;
        }

        @Override
        public boolean specify(MyArray myArray) {
            return myArray.getMyArray().length > minSize;
        }
    }

    private static class NeverMatchSpecification implements MyArraySpecification {
        @Override
        public boolean specify(MyArray myArray) {
            return false;
        }
    }
}