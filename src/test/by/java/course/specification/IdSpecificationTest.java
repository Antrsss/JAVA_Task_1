package test.by.java.course.specification;

import main.by.java.course.entity.MyArray;
import main.by.java.course.specification.impl.IdSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdSpecificationTest {

    private MyArray testArray;

    @BeforeEach
    void setUp() {
        testArray = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B", "C"})
                .build();
    }

    @Test
    @DisplayName("IdSpecification - matching ID")
    void testSpecifyWithMatchingId() {
        long targetId = testArray.getId();
        IdSpecification specification = new IdSpecification(targetId);

        boolean result = specification.specify(testArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("IdSpecification - non-matching ID")
    void testSpecifyWithNonMatchingId() {
        long wrongId = testArray.getId() + 1000;
        IdSpecification specification = new IdSpecification(wrongId);

        boolean result = specification.specify(testArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IdSpecification - zero ID")
    void testSpecifyWithZeroId() {
        IdSpecification specification = new IdSpecification(0);

        boolean result = specification.specify(testArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IdSpecification - negative ID")
    void testSpecifyWithNegativeId() {
        IdSpecification specification = new IdSpecification(-1);

        boolean result = specification.specify(testArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IdSpecification - maximum long ID")
    void testSpecifyWithMaxLongId() {
        IdSpecification specification = new IdSpecification(Long.MAX_VALUE);

        boolean result = specification.specify(testArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IdSpecification - minimum long ID")
    void testSpecifyWithMinLongId() {
        IdSpecification specification = new IdSpecification(Long.MIN_VALUE);

        boolean result = specification.specify(testArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IdSpecification - same ID for different arrays")
    void testSpecifyWithSameIdForDifferentArrays() {
        MyArray array1 = MyArray.newBuilder()
                .setMyArray(new String[]{"X"})
                .build();
        MyArray array2 = MyArray.newBuilder()
                .setMyArray(new String[]{"X"})
                .build();

        assertNotEquals(array1.getId(), array2.getId());

        IdSpecification spec1 = new IdSpecification(array1.getId());
        IdSpecification spec2 = new IdSpecification(array2.getId());

        assertTrue(spec1.specify(array1));
        assertFalse(spec1.specify(array2));
        assertTrue(spec2.specify(array2));
        assertFalse(spec2.specify(array1));
    }

    @Test
    @DisplayName("IdSpecification - record properties")
    void testRecordProperties() {
        long testId = 12345L;
        IdSpecification specification = new IdSpecification(testId);

        assertEquals(testId, specification.id());
    }
}