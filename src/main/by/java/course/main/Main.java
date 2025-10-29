package main.by.java.course.main;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.parser.impl.StringParserImpl;
import main.by.java.course.reader.impl.MyStringReaderImpl;
import main.by.java.course.service.operation.impl.MyArrayOperationImpl;
import main.by.java.course.service.sort.impl.MyArraySortImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String FILE_PATH = "array_files/data.txt";

    public static void main(String[] args) {
        logger.info("Starting MyArray application");

        try {
            String fileContent = readFile();
            if (fileContent == null || fileContent.isBlank()) {
                logger.error("No data to process");
                return;
            }

            String[] stringArray = parseString(fileContent);

            MyArray myArray = createMyArray(stringArray);

            performArrayOperations(myArray);

            performSortingOperations(myArray);

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
            logger.info("Successfully read file content: '{}'", content);
        } else {
            logger.warn("File is empty or contains only whitespace");
        }

        return content;
    }

    private static String[] parseString(String content) throws MyArrayException {
        logger.info("=== STEP 2: Parsing string ===");
        StringParserImpl parser = new StringParserImpl();

        logger.info("Parsing string: '{}'", content);
        String[] result = parser.parseString(content);

        logger.info("Successfully parsed into {} elements", result.length);
        logger.debug("Parsed array: {}", java.util.Arrays.toString(result));

        return result;
    }

    private static MyArray createMyArray(String[] stringArray) {
        logger.info("=== STEP 3: Creating MyArray ===");

        MyArray myArray = MyArray.newBuilder()
                .setMyArray(stringArray)
                .build();

        logger.info("MyArray created with {} elements", myArray.getMyArray().length);
        logger.info("MyArray details: {}", myArray);

        return myArray;
    }

    private static void performArrayOperations(MyArray myArray) throws MyArrayException {
        logger.info("=== STEP 4: Array Operations ===");
        MyArrayOperationImpl operations = new MyArrayOperationImpl();

        String minValue = operations.findMinValue(myArray);
        String maxValue = operations.findMaxValue(myArray);
        logger.info("Min value: {}", minValue);
        logger.info("Max value: {}", maxValue);

        double average = operations.calculateAverageValue(myArray);
        int sum = operations.calculateSum(myArray);
        logger.info("Average value: {:.2f}", average);
        logger.info("Sum: {}", sum);

        int positiveCount = operations.calculatePositiveValues(myArray);
        int negativeCount = operations.calculateNegativeValues(myArray);
        logger.info("Positive values: {}", positiveCount);
        logger.info("Negative values: {}", negativeCount);

        if (myArray.getMyArray().length > 2) {
            MyArray modifiedArray = operations.replaceMyArrayElement(myArray, "REPLACED", 1);
            logger.info("After replacing element at index 1: {}", modifiedArray);

            String newMin = operations.findMinValue(modifiedArray);
            logger.info("Min value in modified array: {}", newMin);
        }

        MyArray copyArray = MyArray.newBuilder()
                .setMyArray(myArray.getMyArray())
                .build();

        logger.info("Original equals copy: {}", myArray.equals(copyArray));
        logger.info("Original hashCode: {}, Copy hashCode: {}",
                myArray.hashCode(), copyArray.hashCode());
    }

    private static void performSortingOperations(MyArray myArray) throws MyArrayException {
        logger.info("=== STEP 5: Sorting Operations ===");
        MyArraySortImpl sorter = new MyArraySortImpl();

        // Selection Sort
        logger.info("--- Selection Sort ---");
        MyArray selectionSorted = sorter.selectionSort(myArray);
        logger.info("Selection sort result: {}", selectionSorted);

        String[] selectionArray = selectionSorted.getMyArray();
        boolean isSelectionSorted = isSorted(selectionArray);
        logger.info("Selection sort verified: {}", isSelectionSorted);

        // Merge Sort
        logger.info("--- Merge Sort ---");
        MyArray mergeSorted = sorter.mergeSort(myArray);
        logger.info("Merge sort result: {}", mergeSorted);

        String[] mergeArray = mergeSorted.getMyArray();
        boolean isMergeSorted = isSorted(mergeArray);
        logger.info("Merge sort verified: {}", isMergeSorted);

        // Quick Sort
        logger.info("--- Quick Sort ---");
        MyArray quickSorted = sorter.quickSort(myArray);
        logger.info("Quick sort result: {}", quickSorted);

        String[] quickArray = quickSorted.getMyArray();
        boolean isQuickSorted = isSorted(quickArray);
        logger.info("Quick sort verified: {}", isQuickSorted);
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