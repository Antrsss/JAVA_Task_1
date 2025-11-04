package by.zgirskaya.course.observer;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.entity.CustomArrayParameters;
import by.zgirskaya.course.observer.impl.CustomArrayObserverImpl;
import by.zgirskaya.course.warehouse.CustomArrayWarehouse;
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
class CustomArrayObserverImplTest {

    private CustomArrayObserverImpl observer;
    private CustomArrayWarehouse warehouse;
    private CustomArray testCustomArray;

    @BeforeEach
    void setUp() throws Exception {
        resetWarehouseSingleton();
        warehouse = CustomArrayWarehouse.getInstance();

        testCustomArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"ABC", "DEF", "GHI"})
                .build();

        observer = new CustomArrayObserverImpl();
    }

    @AfterEach
    void tearDown() throws Exception {
        resetWarehouseSingleton();
    }

    private void resetWarehouseSingleton() throws Exception {
        Field instanceField = CustomArrayWarehouse.class.getDeclaredField("instance");
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
        observer.handleEvent(testCustomArray);

        Map<Integer, CustomArrayParameters> parametersMap = warehouse.getCustomArrayParametersMap();
        assertEquals(1, parametersMap.size());

        CustomArrayParameters storedParameters = parametersMap.get(testCustomArray.getId());
        assertNotNull(storedParameters);
        assertEquals(testCustomArray.getId(), storedParameters.customArrayId());
        assertNotNull(storedParameters.minValue());
        assertNotNull(storedParameters.maxValue());
    }

    @Test
    @DisplayName("HandleEvent with null MyArray does nothing")
    void testHandleEventWithNullMyArray() {
        observer.handleEvent(null);

        Map<Integer, CustomArrayParameters> parametersMap = warehouse.getCustomArrayParametersMap();
        assertTrue(parametersMap.isEmpty());
    }

    @Test
    @DisplayName("HandleEvent with empty MyArray")
    void testHandleEventWithEmptyMyArray() {
        CustomArray emptyArray = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertDoesNotThrow(() -> observer.handleEvent(emptyArray));

        Map<Integer, CustomArrayParameters> parametersMap = warehouse.getCustomArrayParametersMap();

        if (parametersMap.containsKey(emptyArray.getId())) {
            CustomArrayParameters storedParameters = parametersMap.get(emptyArray.getId());
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
        CustomArray singleElementArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"A"})
                .build();

        observer.handleEvent(singleElementArray);

        Map<Integer, CustomArrayParameters> parametersMap = warehouse.getCustomArrayParametersMap();
        assertEquals(1, parametersMap.size());

        CustomArrayParameters storedParameters = parametersMap.get(singleElementArray.getId());
        assertNotNull(storedParameters);
        assertEquals("A", storedParameters.minValue());
        assertEquals("A", storedParameters.maxValue());
    }

    @Test
    @DisplayName("HandleEvent with uppercase letters only")
    void testHandleEventWithUppercaseLettersOnly() {
        CustomArray uppercaseArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"ABC", "DEF"})
                .build();

        observer.handleEvent(uppercaseArray);

        Map<Integer, CustomArrayParameters> parametersMap = warehouse.getCustomArrayParametersMap();
        CustomArrayParameters storedParameters = parametersMap.get(uppercaseArray.getId());

        assertTrue(storedParameters.arraySum() > 0);
        assertTrue(storedParameters.averageValue() > 0);
        assertTrue(storedParameters.positiveValuesCount() > 0);
        assertEquals(0, storedParameters.negativeValuesCount());
    }

    @Test
    @DisplayName("HandleEvent with lowercase letters only")
    void testHandleEventWithLowercaseLettersOnly() {
        CustomArray lowercaseArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"abc", "def"})
                .build();

        observer.handleEvent(lowercaseArray);

        Map<Integer, CustomArrayParameters> parametersMap = warehouse.getCustomArrayParametersMap();
        CustomArrayParameters storedParameters = parametersMap.get(lowercaseArray.getId());

        assertTrue(storedParameters.arraySum() < 0);
        assertTrue(storedParameters.averageValue() < 0);
        assertEquals(0, storedParameters.positiveValuesCount());
        assertTrue(storedParameters.negativeValuesCount() > 0);
    }

    @Test
    @DisplayName("HandleEvent with mixed case letters")
    void testHandleEventWithMixedCaseLetters() {
        CustomArray mixedCaseArray = CustomArray.newBuilder()
                .setMyArray(new String[]{"AbC", "dEf"})
                .build();

        observer.handleEvent(mixedCaseArray);

        Map<Integer, CustomArrayParameters> parametersMap = warehouse.getCustomArrayParametersMap();
        CustomArrayParameters storedParameters = parametersMap.get(mixedCaseArray.getId());

        assertNotNull(storedParameters);
    }

    @Test
    @DisplayName("HandleEvent multiple times with same MyArray")
    void testHandleEventMultipleTimesWithSameMyArray() {
        observer.handleEvent(testCustomArray);

        Map<Integer, CustomArrayParameters> firstParameters = warehouse.getCustomArrayParametersMap();
        assertEquals(1, firstParameters.size());
        CustomArrayParameters firstStored = firstParameters.get(testCustomArray.getId());

        observer.handleEvent(testCustomArray);

        Map<Integer, CustomArrayParameters> secondParameters = warehouse.getCustomArrayParametersMap();
        assertEquals(1, secondParameters.size());
        CustomArrayParameters secondStored = secondParameters.get(testCustomArray.getId());

        assertNotNull(firstStored);
        assertNotNull(secondStored);
    }

    @Test
    @DisplayName("HandleEvent with different MyArrays")
    void testHandleEventWithDifferentMyArrays() {
        CustomArray array1 = CustomArray.newBuilder()
                .setMyArray(new String[]{"A", "B"})
                .build();

        CustomArray array2 = CustomArray.newBuilder()
                .setMyArray(new String[]{"X", "Y", "Z"})
                .build();

        observer.handleEvent(array1);
        observer.handleEvent(array2);

        Map<Integer, CustomArrayParameters> parametersMap = warehouse.getCustomArrayParametersMap();
        assertEquals(2, parametersMap.size());
        assertNotNull(parametersMap.get(array1.getId()));
        assertNotNull(parametersMap.get(array2.getId()));
    }

    @Test
    @DisplayName("HandleEvent continues when MyArrayOperations throws exception")
    void testHandleEventContinuesWhenOperationsThrowException() {
        CustomArray problematicArray = CustomArray.newBuilder()
                .setMyArray(new String[0])
                .build();

        assertDoesNotThrow(() -> observer.handleEvent(problematicArray));
    }
}