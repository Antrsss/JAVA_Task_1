package by.zgirskaya.course.warehouse;

import by.zgirskaya.course.entity.CustomArrayParameters;
import by.zgirskaya.course.exception.CustomArrayException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayWarehouseTest {

    private CustomArrayWarehouse warehouse;
    private CustomArrayParameters testParameters1;
    private CustomArrayParameters testParameters2;

    @BeforeEach
    void setUp() throws Exception {
        resetSingleton();

        warehouse = CustomArrayWarehouse.getInstance();

        testParameters1 = new CustomArrayParameters(
                1, "minValue1", "maxValue1", 10.5, 100, 5, 3
        );

        testParameters2 = new CustomArrayParameters(
                2, "minValue2", "maxValue2", 20.5, 200, 8, 2
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        resetSingleton();
    }

    private void resetSingleton() throws Exception {
        Field instanceField = CustomArrayWarehouse.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    @DisplayName("GetInstance returns same instance")
    void testGetInstanceReturnsSameInstance() {
        CustomArrayWarehouse instance1 = CustomArrayWarehouse.getInstance();
        CustomArrayWarehouse instance2 = CustomArrayWarehouse.getInstance();

        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    @DisplayName("GetInstance creates new instance only once")
    void testGetInstanceCreatesInstanceOnlyOnce() throws Exception {
        CustomArrayWarehouse instance1 = CustomArrayWarehouse.getInstance();
        CustomArrayWarehouse instance2 = CustomArrayWarehouse.getInstance();

        assertSame(instance1, instance2);

        resetSingleton();
        CustomArrayWarehouse instance3 = CustomArrayWarehouse.getInstance();

        assertNotSame(instance1, instance3);
    }

    @Test
    @DisplayName("GetMyArrayParametersMap returns empty map for new warehouse")
    void testGetCustomArrayParametersMapReturnsEmptyMapForNewWarehouse() {
        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("GetMyArrayParametersMap returns copy of internal map")
    void testGetCustomArrayParametersMapReturnsCopy() throws CustomArrayException {
        warehouse.putCustomArrayParametersMap(1, testParameters1);

        Map<Integer, CustomArrayParameters> copy1 = warehouse.getCustomArrayParametersMap();
        Map<Integer, CustomArrayParameters> copy2 = warehouse.getCustomArrayParametersMap();

        assertNotSame(copy1, copy2);

        assertEquals(copy1, copy2);
        assertEquals(1, copy1.size());
        assertEquals(testParameters1, copy1.get(1));
    }

    @Test
    @DisplayName("GetMyArrayParametersMap returns immutable map")
    void testGetCustomArrayParametersMapReturnsImmutableMap() throws CustomArrayException {
        warehouse.putCustomArrayParametersMap(1, testParameters1);
        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();

        assertThrows(UnsupportedOperationException.class,
                () -> result.put(2, testParameters2));
        assertThrows(UnsupportedOperationException.class,
                () -> result.remove(1));
        assertThrows(UnsupportedOperationException.class,
                () -> result.clear());
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with valid parameters")
    void testPutCustomArrayParametersMapWithValidParameters() throws CustomArrayException {
        warehouse.putCustomArrayParametersMap(1, testParameters1);

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(testParameters1, result.get(1));
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with multiple parameters")
    void testPutCustomArrayParametersMapWithMultipleParameters() throws CustomArrayException {
        warehouse.putCustomArrayParametersMap(1, testParameters1);
        warehouse.putCustomArrayParametersMap(2, testParameters2);

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertEquals(2, result.size());
        assertEquals(testParameters1, result.get(1));
        assertEquals(testParameters2, result.get(2));
    }

    @Test
    @DisplayName("PutMyArrayParametersMap overwrites existing parameters")
    void testPutCustomArrayParametersMapOverwritesExisting() throws CustomArrayException {
        warehouse.putCustomArrayParametersMap(1, testParameters1);

        CustomArrayParameters updatedParameters = new CustomArrayParameters(
                1, "newMin", "newMax", 30.5, 300, 10, 1
        );
        warehouse.putCustomArrayParametersMap(1, updatedParameters);

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(updatedParameters, result.get(1));
        assertNotEquals(testParameters1, result.get(1));
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with null parameters throws MyArrayException")
    void testPutCustomArrayParametersMapWithNullParametersThrowsException() {
        CustomArrayException exception = assertThrows(CustomArrayException.class,
                () -> warehouse.putCustomArrayParametersMap(1, null));

        assertEquals("Warehouse: myArrayParameter cannot be null", exception.getMessage());

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with negative ID")
    void testPutCustomArrayParametersMapWithNegativeId() throws CustomArrayException {
        warehouse.putCustomArrayParametersMap(-1, testParameters1);

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(testParameters1, result.get(-1));
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with zero ID")
    void testPutCustomArrayParametersMapWithZeroId() throws CustomArrayException {
        warehouse.putCustomArrayParametersMap(0, testParameters1);

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(testParameters1, result.get(0));
    }

    @Test
    @DisplayName("Multiple instances share same state")
    void testMultipleInstancesShareSameState() throws CustomArrayException {
        warehouse.putCustomArrayParametersMap(1, testParameters1);

        CustomArrayWarehouse anotherInstance = CustomArrayWarehouse.getInstance();

        Map<Integer, CustomArrayParameters> result = anotherInstance.getCustomArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(testParameters1, result.get(1));
    }

    @Test
    @DisplayName("Put parameters with all zero values")
    void testPutParametersWithAllZeroValues() throws CustomArrayException {
        CustomArrayParameters zeroParameters = new CustomArrayParameters(
                5, "zero", "zero", 0.0, 0, 0, 0
        );

        warehouse.putCustomArrayParametersMap(5, zeroParameters);

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertEquals(zeroParameters, result.get(5));
    }

    @Test
    @DisplayName("Put parameters with negative values")
    void testPutParametersWithNegativeValues() throws CustomArrayException {
        CustomArrayParameters negativeParameters = new CustomArrayParameters(
                6, "min", "max", -5.5, -100, 0, 10
        );

        warehouse.putCustomArrayParametersMap(6, negativeParameters);

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertEquals(negativeParameters, result.get(6));
    }

    @Test
    @DisplayName("Put parameters with large ID")
    void testPutParametersWithLargeId() throws CustomArrayException {
        int largeId = Integer.MAX_VALUE;
        warehouse.putCustomArrayParametersMap(largeId, testParameters1);

        Map<Integer, CustomArrayParameters> result = warehouse.getCustomArrayParametersMap();
        assertEquals(testParameters1, result.get(largeId));
    }
}