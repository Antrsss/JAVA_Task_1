package main.by.java.course.main;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.parser.impl.StringParserImpl;
import main.by.java.course.reader.impl.MyStringReaderImpl;
import main.by.java.course.service.operation.impl.MyArrayOperationImpl;
import main.by.java.course.service.sort.impl.MyArraySortImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String FILE_PATH = "resources/file/data.txt";

    public static void main(String[] args) {
        logger.info("Starting MyArray application");

        try {
            String fileContent = readFile();
            if (fileContent == null || fileContent.isBlank()) {
                logger.error("No data to process");
                return;
            }

            String[][] arraysData = parseFile(fileContent);
            processAllArrays(arraysData);

            logger.info("MyArray application completed successfully");

        } catch (MyArrayException e) {
            logger.error("Application error: {}", e.getMessage());
            if (e.getCause() != null) {
                logger.error("Root cause: {}", e.getCause().getMessage());
            }
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
        }
    }

    private static String readFile() throws MyArrayException {
        logger.info("=== STEP 1: Reading file ===");
        MyStringReaderImpl reader = new MyStringReaderImpl();

        logger.info("Reading file from: {}", FILE_PATH);
        String content = reader.readStringFromFile(FILE_PATH);

        if (content != null && !content.isBlank()) {
            logger.info("Successfully read file content ({} characters)", content.length());
            logger.debug("File content:\n{}", content);
        } else {
            logger.warn("File is empty or contains only whitespace");
        }

        return content;
    }

    private static String[][] parseFile(String content) throws MyArrayException {
        logger.info("=== STEP 2: Parsing file ===");
        StringParserImpl parser = new StringParserImpl();

        logger.info("Parsing file content into multiple arrays");
        String[][] arrays = parser.parseFile(content);

        logger.info("Successfully parsed {} arrays from file", arrays.length);
        for (int i = 0; i < arrays.length; i++) {
            logger.info("Array {}: {} elements -> {}", i, arrays[i].length, Arrays.toString(arrays[i]));
        }

        return arrays;
    }

    private static void processAllArrays(String[][] arraysData) throws MyArrayException {
        logger.info("=== STEP 3: Processing all arrays ===");

        for (int i = 0; i < arraysData.length; i++) {
            logger.info("--- Processing Array {} ---", i);
            processSingleArray(arraysData[i], i);
        }

        logger.info("Processed {} arrays total", arraysData.length);
    }

    private static void processSingleArray(String[] stringArray, int arrayIndex) throws MyArrayException {
        MyArray myArray = createMyArray(stringArray, arrayIndex);

        performArrayOperations(myArray, arrayIndex);

        performSortingOperations(myArray, arrayIndex);
    }

    private static MyArray createMyArray(String[] stringArray, int arrayIndex) {
        logger.info("Creating MyArray for array {}", arrayIndex);

        MyArray myArray = MyArray.newBuilder()
                .setMyArray(stringArray)
                .build();

        logger.info("MyArray {} created with {} elements", arrayIndex, myArray.getMyArray().length);
        logger.debug("MyArray {} details: {}", arrayIndex, myArray);

        return myArray;
    }

    private static void performArrayOperations(MyArray myArray, int arrayIndex) throws MyArrayException {
        logger.info("=== Array Operations for array {} ===", arrayIndex);
        MyArrayOperationImpl operations = new MyArrayOperationImpl();

        try {
            String minValue = operations.findMinValue(myArray);
            String maxValue = operations.findMaxValue(myArray);
            logger.info("Array {} - Min value: {}", arrayIndex, minValue);
            logger.info("Array {} - Max value: {}", arrayIndex, maxValue);

            double average = operations.calculateAverageValue(myArray);
            int sum = operations.calculateSum(myArray);
            logger.info("Array {} - Average value: {:.2f}", arrayIndex, average);
            logger.info("Array {} - Sum: {}", arrayIndex, sum);

            int positiveCount = operations.calculatePositiveValues(myArray);
            int negativeCount = operations.calculateNegativeValues(myArray);
            logger.info("Array {} - Positive values: {}", arrayIndex, positiveCount);
            logger.info("Array {} - Negative values: {}", arrayIndex, negativeCount);

            if (myArray.getMyArray().length > 2) {
                MyArray modifiedArray = operations.replaceMyArrayElement(myArray, "REPLACED", 1);
                logger.info("Array {} - After replacing element at index 1: {}", arrayIndex, modifiedArray);

                String newMin = operations.findMinValue(modifiedArray);
                logger.info("Array {} - Min value in modified array: {}", arrayIndex, newMin);
            }

            MyArray copyArray = MyArray.newBuilder()
                    .setMyArray(myArray.getMyArray())
                    .build();

            logger.info("Array {} - Original equals copy: {}", arrayIndex, myArray.equals(copyArray));
            logger.info("Array {} - Original hashCode: {}, Copy hashCode: {}",
                    arrayIndex, myArray.hashCode(), copyArray.hashCode());

        } catch (MyArrayException e) {
            logger.error("Error performing operations on array {}: {}", arrayIndex, e.getMessage());
            throw e;
        }
    }

    private static void performSortingOperations(MyArray myArray, int arrayIndex) throws MyArrayException {
        logger.info("=== Sorting Operations for array {} ===", arrayIndex);
        MyArraySortImpl sorter = new MyArraySortImpl();

        try {
            // Selection Sort
            logger.info("Array {} --- Selection Sort ---", arrayIndex);
            MyArray selectionSorted = sorter.selectionSort(myArray);
            logger.info("Array {} - Selection sort result: {}", arrayIndex, selectionSorted);

            String[] selectionArray = selectionSorted.getMyArray();
            boolean isSelectionSorted = isSorted(selectionArray);
            logger.info("Array {} - Selection sort verified: {}", arrayIndex, isSelectionSorted);

            // Merge Sort
            logger.info("Array {} --- Merge Sort ---", arrayIndex);
            MyArray mergeSorted = sorter.mergeSort(myArray);
            logger.info("Array {} - Merge sort result: {}", arrayIndex, mergeSorted);

            String[] mergeArray = mergeSorted.getMyArray();
            boolean isMergeSorted = isSorted(mergeArray);
            logger.info("Array {} - Merge sort verified: {}", arrayIndex, isMergeSorted);

            // Quick Sort
            logger.info("Array {} --- Quick Sort ---", arrayIndex);
            MyArray quickSorted = sorter.quickSort(myArray);
            logger.info("Array {} - Quick sort result: {}", arrayIndex, quickSorted);

            String[] quickArray = quickSorted.getMyArray();
            boolean isQuickSorted = isSorted(quickArray);
            logger.info("Array {} - Quick sort verified: {}", arrayIndex, isQuickSorted);


            boolean allSortsEqual = Arrays.equals(selectionArray, mergeArray) &&
                    Arrays.equals(mergeArray, quickArray);
            logger.info("Array {} - All sort results identical: {}", arrayIndex, allSortsEqual);

        } catch (MyArrayException e) {
            logger.error("Error performing sorting on array {}: {}", arrayIndex, e.getMessage());
            throw e;
        }
    }

    private static boolean isSorted(String[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }
}