package test.by.java.course.warehouse;

import main.by.java.course.entity.MyArrayParameters;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.warehouse.MyArrayWarehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayWarehouseTest {

    private MyArrayWarehouse warehouse;
    private MyArrayParameters testParameters1;
    private MyArrayParameters testParameters2;

    @BeforeEach
    void setUp() throws Exception {
        resetSingleton();

        warehouse = MyArrayWarehouse.getInstance();

        testParameters1 = new MyArrayParameters(
                1, "minValue1", "maxValue1", 10.5, 100, 5, 3
        );

        testParameters2 = new MyArrayParameters(
                2, "minValue2", "maxValue2", 20.5, 200, 8, 2
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        resetSingleton();
    }

    private void resetSingleton() throws Exception {
        Field instanceField = MyArrayWarehouse.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    @DisplayName("GetInstance returns same instance")
    void testGetInstanceReturnsSameInstance() {
        MyArrayWarehouse instance1 = MyArrayWarehouse.getInstance();
        MyArrayWarehouse instance2 = MyArrayWarehouse.getInstance();

        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    @DisplayName("GetInstance creates new instance only once")
    void testGetInstanceCreatesInstanceOnlyOnce() throws Exception {
        MyArrayWarehouse instance1 = MyArrayWarehouse.getInstance();
        MyArrayWarehouse instance2 = MyArrayWarehouse.getInstance();

        assertSame(instance1, instance2);

        resetSingleton();
        MyArrayWarehouse instance3 = MyArrayWarehouse.getInstance();

        assertNotSame(instance1, instance3);
    }

    @Test
    @DisplayName("GetMyArrayParametersMap returns empty map for new warehouse")
    void testGetMyArrayParametersMapReturnsEmptyMapForNewWarehouse() {
        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("GetMyArrayParametersMap returns copy of internal map")
    void testGetMyArrayParametersMapReturnsCopy() throws MyArrayException {
        warehouse.putMyArrayParametersMap(1, testParameters1);

        Map<Integer, MyArrayParameters> copy1 = warehouse.getMyArrayParametersMap();
        Map<Integer, MyArrayParameters> copy2 = warehouse.getMyArrayParametersMap();

        assertNotSame(copy1, copy2);

        assertEquals(copy1, copy2);
        assertEquals(1, copy1.size());
        assertEquals(testParameters1, copy1.get(1));
    }

    @Test
    @DisplayName("GetMyArrayParametersMap returns immutable map")
    void testGetMyArrayParametersMapReturnsImmutableMap() throws MyArrayException {
        warehouse.putMyArrayParametersMap(1, testParameters1);
        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();

        assertThrows(UnsupportedOperationException.class,
                () -> result.put(2, testParameters2));
        assertThrows(UnsupportedOperationException.class,
                () -> result.remove(1));
        assertThrows(UnsupportedOperationException.class,
                () -> result.clear());
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with valid parameters")
    void testPutMyArrayParametersMapWithValidParameters() throws MyArrayException {
        warehouse.putMyArrayParametersMap(1, testParameters1);

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(testParameters1, result.get(1));
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with multiple parameters")
    void testPutMyArrayParametersMapWithMultipleParameters() throws MyArrayException {
        warehouse.putMyArrayParametersMap(1, testParameters1);
        warehouse.putMyArrayParametersMap(2, testParameters2);

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertEquals(2, result.size());
        assertEquals(testParameters1, result.get(1));
        assertEquals(testParameters2, result.get(2));
    }

    @Test
    @DisplayName("PutMyArrayParametersMap overwrites existing parameters")
    void testPutMyArrayParametersMapOverwritesExisting() throws MyArrayException {
        warehouse.putMyArrayParametersMap(1, testParameters1);

        MyArrayParameters updatedParameters = new MyArrayParameters(
                1, "newMin", "newMax", 30.5, 300, 10, 1
        );
        warehouse.putMyArrayParametersMap(1, updatedParameters);

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(updatedParameters, result.get(1));
        assertNotEquals(testParameters1, result.get(1));
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with null parameters throws MyArrayException")
    void testPutMyArrayParametersMapWithNullParametersThrowsException() {
        MyArrayException exception = assertThrows(MyArrayException.class,
                () -> warehouse.putMyArrayParametersMap(1, null));

        assertEquals("Warehouse: myArrayParameter cannot be null", exception.getMessage());

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with negative ID")
    void testPutMyArrayParametersMapWithNegativeId() throws MyArrayException {
        warehouse.putMyArrayParametersMap(-1, testParameters1);

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(testParameters1, result.get(-1));
    }

    @Test
    @DisplayName("PutMyArrayParametersMap with zero ID")
    void testPutMyArrayParametersMapWithZeroId() throws MyArrayException {
        warehouse.putMyArrayParametersMap(0, testParameters1);

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(testParameters1, result.get(0));
    }

    @Test
    @DisplayName("Multiple instances share same state")
    void testMultipleInstancesShareSameState() throws MyArrayException {
        warehouse.putMyArrayParametersMap(1, testParameters1);

        MyArrayWarehouse anotherInstance = MyArrayWarehouse.getInstance();

        Map<Integer, MyArrayParameters> result = anotherInstance.getMyArrayParametersMap();
        assertEquals(1, result.size());
        assertEquals(testParameters1, result.get(1));
    }

    @Test
    @DisplayName("Put parameters with all zero values")
    void testPutParametersWithAllZeroValues() throws MyArrayException {
        MyArrayParameters zeroParameters = new MyArrayParameters(
                5, "zero", "zero", 0.0, 0, 0, 0
        );

        warehouse.putMyArrayParametersMap(5, zeroParameters);

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertEquals(zeroParameters, result.get(5));
    }

    @Test
    @DisplayName("Put parameters with negative values")
    void testPutParametersWithNegativeValues() throws MyArrayException {
        MyArrayParameters negativeParameters = new MyArrayParameters(
                6, "min", "max", -5.5, -100, 0, 10
        );

        warehouse.putMyArrayParametersMap(6, negativeParameters);

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertEquals(negativeParameters, result.get(6));
    }

    @Test
    @DisplayName("Put parameters with large ID")
    void testPutParametersWithLargeId() throws MyArrayException {
        int largeId = Integer.MAX_VALUE;
        warehouse.putMyArrayParametersMap(largeId, testParameters1);

        Map<Integer, MyArrayParameters> result = warehouse.getMyArrayParametersMap();
        assertEquals(testParameters1, result.get(largeId));
    }
}