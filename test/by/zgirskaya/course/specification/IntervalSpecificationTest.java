package by.zgirskaya.course.specification;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.specification.impl.IntervalSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalSpecificationTest {

    private CustomArray uppercaseArray;
    private CustomArray lowercaseArray;
    private CustomArray mixedCaseArray;

    @BeforeEach
    void setUp() {
        uppercaseArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"ABC", "DEF"}) // ABC=65+66+67=198, DEF=68+69+70=207
                .build();

        lowercaseArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"abc", "def"}) // abc=-97-98-99=-294, def=-100-101-102=-303
                .build();

        mixedCaseArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"AbC", "dEf"}) // AbC=65-98+67=34, dEf=-100+69-102=-133
                .build();
    }

    @Test
    @DisplayName("IntervalSpecification - all elements within interval")
    void testSpecifyWithAllElementsWithinInterval() {
        //[190, 210]
        IntervalSpecification specification = new IntervalSpecification(190, 210);

        boolean result = specification.specify(uppercaseArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("IntervalSpecification - element below minimum")
    void testSpecifyWithElementBelowMinimum() {
        IntervalSpecification specification = new IntervalSpecification(-300, 0);

        boolean result = specification.specify(lowercaseArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IntervalSpecification - element above maximum")
    void testSpecifyWithElementAboveMaximum() {
        IntervalSpecification specification = new IntervalSpecification(0, 200);

        boolean result = specification.specify(uppercaseArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IntervalSpecification - exact boundary values")
    void testSpecifyWithExactBoundaryValues() {
        IntervalSpecification specification = new IntervalSpecification(198, 207);

        boolean result = specification.specify(uppercaseArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("IntervalSpecification - negative interval")
    void testSpecifyWithNegativeInterval() {
        IntervalSpecification specification = new IntervalSpecification(-400, -200);

        boolean result = specification.specify(lowercaseArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("IntervalSpecification - mixed positive and negative interval")
    void testSpecifyWithMixedInterval() {
        IntervalSpecification specification = new IntervalSpecification(-200, 100);

        boolean result = specification.specify(mixedCaseArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("IntervalSpecification - single element array within interval")
    void testSpecifyWithSingleElementArray() {
        CustomArray singleElementArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"X"})
                .build();
        IntervalSpecification specification = new IntervalSpecification(0, 100);

        boolean result = specification.specify(singleElementArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("IntervalSpecification - single element array outside interval")
    void testSpecifyWithSingleElementArrayOutside() {
        CustomArray singleElementArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"XYZ"})
                .build();
        IntervalSpecification specification = new IntervalSpecification(0, 200);

        boolean result = specification.specify(singleElementArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IntervalSpecification - empty array")
    void testSpecifyWithEmptyArray() {
        CustomArray emptyArray = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();
        IntervalSpecification specification = new IntervalSpecification(0, 100);

        boolean result = specification.specify(emptyArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("IntervalSpecification - zero width interval")
    void testSpecifyWithZeroWidthInterval() {
        IntervalSpecification specification = new IntervalSpecification(200, 200);

        boolean result = specification.specify(uppercaseArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IntervalSpecification - very wide interval")
    void testSpecifyWithVeryWideInterval() {
        IntervalSpecification specification = new IntervalSpecification(-1000, 1000);

        boolean result1 = specification.specify(uppercaseArray);
        boolean result2 = specification.specify(lowercaseArray);
        boolean result3 = specification.specify(mixedCaseArray);

        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
    }

    @Test
    @DisplayName("IntervalSpecification - invalid interval (min > max)")
    void testSpecifyWithInvalidInterval() {
        IntervalSpecification specification = new IntervalSpecification(100, 0);

        boolean result = specification.specify(uppercaseArray);

        assertFalse(result);
    }

    @Test
    @DisplayName("IntervalSpecification - elements with special characters")
    void testSpecifyWithSpecialCharacters() {
        CustomArray specialArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"A1B2", "C3D4"})
                .build();
        IntervalSpecification specification = new IntervalSpecification(130, 140);

        boolean result = specification.specify(specialArray);

        assertTrue(result);
    }

    @Test
    @DisplayName("IntervalSpecification - record properties")
    void testRecordProperties() {
        IntervalSpecification specification = new IntervalSpecification(10, 20);

        assertEquals(10, specification.minValue());
        assertEquals(20, specification.maxValue());
    }

    @Test
    @DisplayName("IntervalSpecification - first element violates interval")
    void testSpecifyFirstElementViolates() {
        CustomArray array = CustomArray.newBuilder()
                .setMyArray(new String[]{"XYZ", "A"})
                .build();
        IntervalSpecification specification = new IntervalSpecification(0, 200);

        boolean result = specification.specify(array);

        assertFalse(result);
    }
}