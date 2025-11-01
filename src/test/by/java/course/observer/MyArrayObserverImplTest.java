package test.by.java.course.observer;

import main.by.java.course.entity.MyArray;
import main.by.java.course.entity.MyArrayParameters;
import main.by.java.course.observer.impl.MyArrayObserverImpl;
import main.by.java.course.warehouse.MyArrayWarehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MyArrayObserverImplTest {

    private MyArrayObserverImpl observer;
    private MyArrayWarehouse warehouse;
    private MyArray testMyArray;

    @BeforeEach
    void setUp() throws Exception {
        resetWarehouseSingleton();
        warehouse = MyArrayWarehouse.getInstance();

        testMyArray = MyArray.newBuilder()
                .setMyArray(new String[]{"ABC", "DEF", "GHI"})
                .build();

        observer = new MyArrayObserverImpl();
    }

    @AfterEach
    void tearDown() throws Exception {
        resetWarehouseSingleton();
    }

    private void resetWarehouseSingleton() throws Exception {
        Field instanceField = MyArrayWarehouse.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    @Test
    @DisplayName("Constructor initializes dependencies")
    void testConstructorInitializesDependencies() {
        assertNotNull(observer);
        assertNotNull(warehouse);
    }

    @Test
    @DisplayName("HandleEvent with valid MyArray updates warehouse")
    void testHandleEventWithValidMyArrayUpdatesWarehouse() {
        observer.handleEvent(testMyArray);

        Map<Integer, MyArrayParameters> parametersMap = warehouse.getMyArrayParametersMap();
        assertEquals(1, parametersMap.size());

        MyArrayParameters storedParameters = parametersMap.get(testMyArray.getId());
        assertNotNull(storedParameters);
        assertEquals(testMyArray.getId(), storedParameters.myArrayId());
        assertNotNull(storedParameters.minValue());
        assertNotNull(storedParameters.maxValue());
    }

    @Test
    @DisplayName("HandleEvent with null MyArray does nothing")
    void testHandleEventWithNullMyArray() {
        observer.handleEvent(null);

        Map<Integer, MyArrayParameters> parametersMap = warehouse.getMyArrayParametersMap();
        assertTrue(parametersMap.isEmpty());
    }

    @Test
    @DisplayName("HandleEvent with empty MyArray")
    void testHandleEventWithEmptyMyArray() {
        MyArray emptyArray = MyArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertDoesNotThrow(() -> observer.handleEvent(emptyArray));

        Map<Integer, MyArrayParameters> parametersMap = warehouse.getMyArrayParametersMap();

        if (parametersMap.containsKey(emptyArray.getId())) {
            MyArrayParameters storedParameters = parametersMap.get(emptyArray.getId());
            assertNotNull(storedParameters, "1");
            assertEquals(0, storedParameters.arraySum());
            assertEquals(0.0, storedParameters.averageValue());
            assertEquals(0, storedParameters.positiveValuesCount());
            assertEquals(0, storedParameters.negativeValuesCount());
        } else {
            assertEquals(0, parametersMap.size(), "Here");
        }
    }

    @Test
    @DisplayName("HandleEvent with single element MyArray")
    void testHandleEventWithSingleElementMyArray() {
        MyArray singleElementArray = MyArray.newBuilder()
                .setMyArray(new String[]{"A"})
                .build();

        observer.handleEvent(singleElementArray);

        Map<Integer, MyArrayParameters> parametersMap = warehouse.getMyArrayParametersMap();
        assertEquals(1, parametersMap.size());

        MyArrayParameters storedParameters = parametersMap.get(singleElementArray.getId());
        assertNotNull(storedParameters);
        assertEquals("A", storedParameters.minValue());
        assertEquals("A", storedParameters.maxValue());
    }

    @Test
    @DisplayName("HandleEvent with uppercase letters only")
    void testHandleEventWithUppercaseLettersOnly() {
        MyArray uppercaseArray = MyArray.newBuilder()
                .setMyArray(new String[]{"ABC", "DEF"})
                .build();

        observer.handleEvent(uppercaseArray);

        Map<Integer, MyArrayParameters> parametersMap = warehouse.getMyArrayParametersMap();
        MyArrayParameters storedParameters = parametersMap.get(uppercaseArray.getId());

        assertTrue(storedParameters.arraySum() > 0);
        assertTrue(storedParameters.averageValue() > 0);
        assertTrue(storedParameters.positiveValuesCount() > 0);
        assertEquals(0, storedParameters.negativeValuesCount());
    }

    @Test
    @DisplayName("HandleEvent with lowercase letters only")
    void testHandleEventWithLowercaseLettersOnly() {
        MyArray lowercaseArray = MyArray.newBuilder()
                .setMyArray(new String[]{"abc", "def"})
                .build();

        observer.handleEvent(lowercaseArray);

        Map<Integer, MyArrayParameters> parametersMap = warehouse.getMyArrayParametersMap();
        MyArrayParameters storedParameters = parametersMap.get(lowercaseArray.getId());

        assertTrue(storedParameters.arraySum() < 0);
        assertTrue(storedParameters.averageValue() < 0);
        assertEquals(0, storedParameters.positiveValuesCount());
        assertTrue(storedParameters.negativeValuesCount() > 0);
    }

    @Test
    @DisplayName("HandleEvent with mixed case letters")
    void testHandleEventWithMixedCaseLetters() {
        MyArray mixedCaseArray = MyArray.newBuilder()
                .setMyArray(new String[]{"AbC", "dEf"})
                .build();

        observer.handleEvent(mixedCaseArray);

        Map<Integer, MyArrayParameters> parametersMap = warehouse.getMyArrayParametersMap();
        MyArrayParameters storedParameters = parametersMap.get(mixedCaseArray.getId());

        assertNotNull(storedParameters);
    }

    @Test
    @DisplayName("HandleEvent multiple times with same MyArray")
    void testHandleEventMultipleTimesWithSameMyArray() {
        observer.handleEvent(testMyArray);

        Map<Integer, MyArrayParameters> firstParameters = warehouse.getMyArrayParametersMap();
        assertEquals(1, firstParameters.size());
        MyArrayParameters firstStored = firstParameters.get(testMyArray.getId());

        observer.handleEvent(testMyArray);

        Map<Integer, MyArrayParameters> secondParameters = warehouse.getMyArrayParametersMap();
        assertEquals(1, secondParameters.size());
        MyArrayParameters secondStored = secondParameters.get(testMyArray.getId());

        assertNotNull(firstStored);
        assertNotNull(secondStored);
    }

    @Test
    @DisplayName("HandleEvent with different MyArrays")
    void testHandleEventWithDifferentMyArrays() {
        MyArray array1 = MyArray.newBuilder()
                .setMyArray(new String[]{"A", "B"})
                .build();

        MyArray array2 = MyArray.newBuilder()
                .setMyArray(new String[]{"X", "Y", "Z"})
                .build();

        observer.handleEvent(array1);
        observer.handleEvent(array2);

        Map<Integer, MyArrayParameters> parametersMap = warehouse.getMyArrayParametersMap();
        assertEquals(2, parametersMap.size());
        assertNotNull(parametersMap.get(array1.getId()));
        assertNotNull(parametersMap.get(array2.getId()));
    }

    @Test
    @DisplayName("HandleEvent continues when MyArrayOperations throws exception")
    void testHandleEventContinuesWhenOperationsThrowException() {
        MyArray problematicArray = MyArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertDoesNotThrow(() -> observer.handleEvent(problematicArray));
    }
}