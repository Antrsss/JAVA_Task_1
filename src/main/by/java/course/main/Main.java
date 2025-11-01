package main.by.java.course.main;

import main.by.java.course.entity.MyArray;
import main.by.java.course.exception.MyArrayException;
import main.by.java.course.observer.impl.MyArrayObserverImpl;
import main.by.java.course.parser.impl.StringParserImpl;
import main.by.java.course.reader.impl.MyStringReaderImpl;
import main.by.java.course.service.operation.impl.MyArrayOperationImpl;
import main.by.java.course.service.sort.impl.MyArraySortImpl;
import main.by.java.course.warehouse.MyArrayWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.util.Arrays;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String FILE_PATH = "resources/file/data.txt";

    public static void main(String[] args) {
        System.out.println("DEBUG enabled: " + logger.isDebugEnabled());
        System.out.println("INFO enabled: " + logger.isInfoEnabled());
        System.out.println("WARN enabled: " + logger.isWarnEnabled());
        System.out.println("ERROR enabled: " + logger.isErrorEnabled());

        logger.info("🚀 Starting MyArray Application");

        try {
            ApplicationResult result = runApplication();
            logApplicationResult(result);

        } catch (MyArrayException e) {
            handleApplicationError(e);
        } catch (Exception e) {
            handleUnexpectedError(e);
        }
    }

    private static ApplicationResult runApplication() throws MyArrayException {
        ApplicationResult result = new ApplicationResult();

        // Шаг 1: Чтение файла
        String fileContent = readFile();
        if (fileContent == null || fileContent.isBlank()) {
            logger.warn("📭 No data found in file");
            return result;
        }

        // Шаг 2: Парсинг файла
        String[][] arraysData = parseFile(fileContent);
        result.setTotalArrays(arraysData.length);

        // Шаг 3: Обработка массивов
        processAllArrays(arraysData, result);

        result.setSuccess(true);
        return result;
    }

    private static String readFile() throws MyArrayException {
        logger.info("📖 === STEP 1: Reading File ===");
        MyStringReaderImpl reader = new MyStringReaderImpl();

        logger.info("Reading from: {}", FILE_PATH);
        String content = reader.readStringFromFile(FILE_PATH);

        if (content != null && !content.isBlank()) {
            logger.info("✅ Successfully read {} characters", content.length());
            if (logger.isDebugEnabled()) {
                logger.debug("File content preview:\n{}", getContentPreview(content));
            }
        } else {
            logger.warn("📭 File is empty or contains only whitespace");
        }

        return content;
    }

    private static String getContentPreview(String content) {
        if (content.length() <= 200) {
            return content;
        }
        return content.substring(0, 200) + "...\n[Content truncated. Total length: " + content.length() + " characters]";
    }

    private static String[][] parseFile(String content) throws MyArrayException {
        logger.info("🔍 === STEP 2: Parsing File ===");
        StringParserImpl parser = new StringParserImpl();

        logger.info("Parsing file content...");
        String[][] arrays = parser.parseFile(content);

        logger.info("✅ Successfully parsed {} arrays", arrays.length);
        logParsedArrays(arrays);

        return arrays;
    }

    private static void logParsedArrays(String[][] arrays) {
        if (logger.isInfoEnabled()) {
            for (int i = 0; i < arrays.length; i++) {
                logger.info("Array {}: {} elements -> {}", i, arrays[i].length,
                        getArrayPreview(arrays[i]));
            }
        }
    }

    private static String getArrayPreview(String[] array) {
        if (array.length <= 5) {
            return Arrays.toString(array);
        }
        String[] preview = Arrays.copyOf(array, 5);
        return Arrays.toString(preview) + "... [and " + (array.length - 5) + " more]";
    }

    private static void processAllArrays(String[][] arraysData, ApplicationResult result) throws MyArrayException {
        logger.info("⚙️ === STEP 3: Processing {} Arrays ===", arraysData.length);

        for (int i = 0; i < arraysData.length; i++) {
            try {
                logger.info("--- Processing Array {}/{} ---", i + 1, arraysData.length);
                boolean success = processSingleArray(arraysData[i], i);

                if (success) {
                    result.incrementProcessedArrays();
                } else {
                    result.incrementFailedArrays();
                }

            } catch (MyArrayException e) {
                logger.error("❌ Failed to process array {}: {}", i, e.getMessage());
                result.incrementFailedArrays();
                // Продолжаем обработку остальных массивов
            }
        }

        logger.info("📊 Processing completed: {}/{} arrays processed successfully",
                result.getProcessedArrays(), result.getTotalArrays());
    }

    private static boolean processSingleArray(String[] stringArray, int arrayIndex) throws MyArrayException {
        try {
            // Создаем MyArray и прикрепляем observer
            MyArray myArray = createMyArrayWithObserver(stringArray, arrayIndex);

            // Выполняем операции
            performArrayOperations(myArray, arrayIndex);

            // Выполняем сортировки
            performSortingOperations(myArray, arrayIndex);

            // Демонстрируем работу с warehouse
            demonstrateWarehouseUsage(myArray, arrayIndex);

            return true;

        } catch (MyArrayException e) {
            logger.error("Array {} processing failed", arrayIndex, e);
            throw e;
        }
    }

    private static MyArray createMyArrayWithObserver(String[] stringArray, int arrayIndex) {
        logger.info("Creating MyArray for array {}", arrayIndex);

        MyArray myArray = MyArray.newBuilder()
                .setMyArray(stringArray)
                .build();

        // Прикрепляем observer для автоматического обновления warehouse
        MyArrayObserverImpl observer = new MyArrayObserverImpl();
        myArray.attach(observer);

        logger.info("✅ MyArray {} created with {} elements (ID: {})",
                arrayIndex, myArray.getMyArray().length, myArray.getId());

        return myArray;
    }

    private static void performArrayOperations(MyArray myArray, int arrayIndex) throws MyArrayException {
        logger.info("📊 === Array Operations for array {} ===", arrayIndex);
        MyArrayOperationImpl operations = new MyArrayOperationImpl();

        // Базовые операции
        String minValue = operations.findMinValue(myArray);
        String maxValue = operations.findMaxValue(myArray);
        logger.info("📈 Min value: '{}', Max value: '{}'", minValue, maxValue);

        // Статистические операции
        double average = operations.calculateAverageValue(myArray);
        int sum = operations.calculateSum(myArray);
        logger.info("🧮 Sum: {}, Average: {:.2f}", sum, average);

        int positiveCount = operations.calculatePositiveValues(myArray);
        int negativeCount = operations.calculateNegativeValues(myArray);
        logger.info("📊 Positive values: {}, Negative values: {}", positiveCount, negativeCount);

        // Демонстрация модификации
        if (myArray.getMyArray().length > 2) {
            demonstrateArrayModification(myArray, operations, arrayIndex);
        }

        // Демонстрация equals/hashCode
        demonstrateEquality(myArray, arrayIndex);
    }

    private static void demonstrateArrayModification(MyArray myArray, MyArrayOperationImpl operations, int arrayIndex)
            throws MyArrayException {
        logger.info("🔄 Demonstrating array modification...");

        MyArray modifiedArray = operations.replaceMyArrayElement(myArray, "MODIFIED", 1);
        logger.info("✅ Modified array: {}", getArrayPreview(modifiedArray.getMyArray()));

        String newMin = operations.findMinValue(modifiedArray);
        logger.info("📈 New min value after modification: '{}'", newMin);
    }

    private static void demonstrateEquality(MyArray myArray, int arrayIndex) {
        MyArray copyArray = MyArray.newBuilder()
                .setMyArray(myArray.getMyArray())
                .build();

        boolean isEqual = myArray.equals(copyArray);
        logger.info("⚖️ Original equals copy: {}", isEqual);

        if (logger.isDebugEnabled()) {
            logger.debug("🔑 Original hashCode: {}, Copy hashCode: {}",
                    myArray.hashCode(), copyArray.hashCode());
        }
    }

    private static void performSortingOperations(MyArray myArray, int arrayIndex) throws MyArrayException {
        logger.info("🔢 === Sorting Operations for array {} ===", arrayIndex);
        MyArraySortImpl sorter = new MyArraySortImpl();

        // Selection Sort
        logger.info("1. Selection Sort");
        MyArray selectionSorted = sorter.selectionSort(myArray);
        boolean isSelectionSorted = isSorted(selectionSorted.getMyArray());
        logger.info("   ✅ Sorted: {}, Verified: {}",
                getArrayPreview(selectionSorted.getMyArray()), isSelectionSorted);

        // Merge Sort
        logger.info("2. Merge Sort");
        MyArray mergeSorted = sorter.mergeSort(myArray);
        boolean isMergeSorted = isSorted(mergeSorted.getMyArray());
        logger.info("   ✅ Sorted: {}, Verified: {}",
                getArrayPreview(mergeSorted.getMyArray()), isMergeSorted);

        // Quick Sort
        logger.info("3. Quick Sort");
        MyArray quickSorted = sorter.quickSort(myArray);
        boolean isQuickSorted = isSorted(quickSorted.getMyArray());
        logger.info("   ✅ Sorted: {}, Verified: {}",
                getArrayPreview(quickSorted.getMyArray()), isQuickSorted);

        // Сравнение результатов
        compareSortResults(selectionSorted, mergeSorted, quickSorted, arrayIndex);
    }

    private static void compareSortResults(MyArray selection, MyArray merge, MyArray quick, int arrayIndex) {
        String[] selectionArray = selection.getMyArray();
        String[] mergeArray = merge.getMyArray();
        String[] quickArray = quick.getMyArray();

        boolean allEqual = Arrays.equals(selectionArray, mergeArray) &&
                Arrays.equals(mergeArray, quickArray);

        logger.info("📋 Sort comparison for array {}: {}", arrayIndex,
                allEqual ? "✅ All algorithms produced identical results" : "⚠️ Results differ between algorithms");
    }

    private static void demonstrateWarehouseUsage(MyArray myArray, int arrayIndex) {
        logger.info("🏪 === Warehouse Demo for array {} ===", arrayIndex);

        MyArrayWarehouse warehouse = MyArrayWarehouse.getInstance();
        var parameters = warehouse.getMyArrayParametersMap();

        if (parameters.containsKey(myArray.getId())) {
            var storedParams = parameters.get(myArray.getId());
            logger.info("📦 Stored parameters for array {}:", arrayIndex);
            logger.info("   ID: {}, Min: '{}', Max: '{}'",
                    storedParams.myArrayId(), storedParams.minValue(), storedParams.maxValue());
            logger.info("   Avg: {:.2f}, Sum: {}, Pos: {}, Neg: {}",
                    storedParams.averageValue(), storedParams.arraySum(),
                    storedParams.positiveValuesCount(), storedParams.negativeValuesCount());
        } else {
            logger.info("📭 No parameters stored in warehouse for array {}", arrayIndex);
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

    private static void logApplicationResult(ApplicationResult result) {
        if (result.isSuccess()) {
            logger.info("🎉 Application completed successfully!");
            logger.info("📊 Summary: Processed {}/{} arrays",
                    result.getProcessedArrays(), result.getTotalArrays());
            if (result.getFailedArrays() > 0) {
                logger.warn("⚠️ {} arrays failed during processing", result.getFailedArrays());
            }
        } else {
            logger.warn("🔚 Application completed with no data processed");
        }
    }

    private static void handleApplicationError(MyArrayException e) {
        logger.error("❌ Application error: {}", e.getMessage());
        if (e.getCause() != null) {
            logger.error("🔍 Root cause: {}", e.getCause().getMessage());
        }
        logger.error("💥 Application terminated due to error");
    }

    private static void handleUnexpectedError(Exception e) {
        logger.error("💥 Unexpected error: {}", e.getMessage());
        logger.error("🔍 Stack trace:", e);
    }

    // Вспомогательный класс для отслеживания результатов приложения
    private static class ApplicationResult {
        private boolean success = false;
        private int totalArrays = 0;
        private int processedArrays = 0;
        private int failedArrays = 0;

        // Getters and setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public int getTotalArrays() { return totalArrays; }
        public void setTotalArrays(int totalArrays) { this.totalArrays = totalArrays; }

        public int getProcessedArrays() { return processedArrays; }
        public void incrementProcessedArrays() { this.processedArrays++; }

        public int getFailedArrays() { return failedArrays; }
        public void incrementFailedArrays() { this.failedArrays++; }
    }
}