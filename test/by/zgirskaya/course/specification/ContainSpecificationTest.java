package by.zgirskaya.course.specification;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.specification.impl.ContainSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainSpecificationTest {

    private CustomArray testArray;

    @BeforeEach
    void setUp() {
        testArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"Apple", "Banana", "Cherry", "Date"})
                .build();
    }

    @Test
    @DisplayName("ContainSpecification - element found in array")
    void testSpecifyWithElementFound() {
        ContainSpecification specification = new ContainSpecification("Banana");

        boolean result = specification.specify(testArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("ContainSpecification - element not found in array")
    void testSpecifyWithElementNotFound() {
        ContainSpecification specification = new ContainSpecification("Elderberry");

        boolean result = specification.specify(testArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("ContainSpecification - case sensitive matching")
    void testSpecifyCaseSensitive() {
        ContainSpecification specification1 = new ContainSpecification("apple"); // lowercase
        ContainSpecification specification2 = new ContainSpecification("Apple"); // uppercase

        boolean result1 = specification1.specify(testArray);
        boolean result2 = specification2.specify(testArray);

        assertFalse(result1); // "apple" not found
        assertTrue(result2);  // "Apple" found
    }

    @Test
    @DisplayName("ContainSpecification - first element match")
    void testSpecifyFirstElement() {
        ContainSpecification specification = new ContainSpecification("Apple");

        boolean result = specification.specify(testArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("ContainSpecification - last element match")
    void testSpecifyLastElement() {
        ContainSpecification specification = new ContainSpecification("Date");

        boolean result = specification.specify(testArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("ContainSpecification - empty array")
    void testSpecifyWithEmptyArray() {
        CustomArray emptyArray = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();
        ContainSpecification specification = new ContainSpecification("Any");

        boolean result = specification.specify(emptyArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("ContainSpecification - search for null element")
    void testSpecifySearchForNullElement() {
        ContainSpecification specification = new ContainSpecification(null);

        assertDoesNotThrow(() -> {
            boolean result = specification.specify(testArray);
            assertFalse(result);
        });
    }

    @Test
    @DisplayName("ContainSpecification - empty search element")
    void testSpecifyWithEmptySearchElement() {
        ContainSpecification specification = new ContainSpecification("");

        boolean result = specification.specify(testArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("ContainSpecification - record properties")
    void testRecordProperties() {
        ContainSpecification specification = new ContainSpecification("Test");

        assertEquals("Test", specification.customArrayElement());
    }
}