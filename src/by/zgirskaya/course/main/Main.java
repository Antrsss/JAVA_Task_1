package by.zgirskaya.course.main;

import by.zgirskaya.course.entity.CustomArray;
import by.zgirskaya.course.exception.CustomArrayException;
import by.zgirskaya.course.observer.impl.CustomArrayObserverImpl;
import by.zgirskaya.course.parser.impl.CustomStringParserImpl;
import by.zgirskaya.course.reader.impl.CustomStringReaderImpl;
import by.zgirskaya.course.service.operation.impl.CustomArrayOperationImpl;
import by.zgirskaya.course.service.sort.impl.CustomArraySortImpl;
import by.zgirskaya.course.warehouse.CustomArrayWarehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        } catch (CustomArrayException e) {
            handleApplicationError(e);
        } catch (Exception e) {
            handleUnexpectedError(e);
        }
    }

    private static ApplicationResult runApplication() throws CustomArrayException {
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

    private static String readFile() throws CustomArrayException {
        logger.info("📖 === STEP 1: Reading File ===");
        CustomStringReaderImpl reader = new CustomStringReaderImpl();

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

    private static String[][] parseFile(String content) throws CustomArrayException {
        logger.info("🔍 === STEP 2: Parsing File ===");
        CustomStringParserImpl parser = new CustomStringParserImpl();

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

    private static void processAllArrays(String[][] arraysData, ApplicationResult result) throws CustomArrayException {
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

            } catch (CustomArrayException e) {
                logger.error("❌ Failed to process array {}: {}", i, e.getMessage());
                result.incrementFailedArrays();
                // Продолжаем обработку остальных массивов
            }
        }

        logger.info("📊 Processing completed: {}/{} arrays processed successfully",
                result.getProcessedArrays(), result.getTotalArrays());
    }

    private static boolean processSingleArray(String[] stringArray, int arrayIndex) throws CustomArrayException {
        try {
            // Создаем MyArray и прикрепляем observer
            CustomArray customArray = createMyArrayWithObserver(stringArray, arrayIndex);

            // Выполняем операции
            performArrayOperations(customArray, arrayIndex);

            // Выполняем сортировки
            performSortingOperations(customArray, arrayIndex);

            // Демонстрируем работу с warehouse
            demonstrateWarehouseUsage(customArray, arrayIndex);

            return true;

        } catch (CustomArrayException e) {
            logger.error("Array {} processing failed", arrayIndex, e);
            throw e;
        }
    }

    private static CustomArray createMyArrayWithObserver(String[] stringArray, int arrayIndex) {
        logger.info("Creating MyArray for array {}", arrayIndex);

        CustomArray customArray = CustomArray.newBuilder()
                .setMyArray(stringArray)
                .build();

        // Прикрепляем observer для автоматического обновления warehouse
        CustomArrayObserverImpl observer = new CustomArrayObserverImpl();
        customArray.attach(observer);

        logger.info("✅ MyArray {} created with {} elements (ID: {})",
                arrayIndex, customArray.getMyArray().length, customArray.getId());

        return customArray;
    }

    private static void performArrayOperations(CustomArray customArray, int arrayIndex) throws CustomArrayException {
        logger.info("📊 === Array Operations for array {} ===", arrayIndex);
        CustomArrayOperationImpl operations = new CustomArrayOperationImpl();

        // Базовые операции
        String minValue = operations.findMinValue(customArray);
        String maxValue = operations.findMaxValue(customArray);
        logger.info("📈 Min value: '{}', Max value: '{}'", minValue, maxValue);

        // Статистические операции
        double average = operations.calculateAverageValue(customArray);
        int sum = operations.calculateSum(customArray);
        logger.info("🧮 Sum: {}, Average: {:.2f}", sum, average);

        int positiveCount = operations.calculatePositiveValues(customArray);
        int negativeCount = operations.calculateNegativeValues(customArray);
        logger.info("📊 Positive values: {}, Negative values: {}", positiveCount, negativeCount);

        // Демонстрация модификации
        if (customArray.getMyArray().length > 2) {
            demonstrateArrayModification(customArray, operations, arrayIndex);
        }

        // Демонстрация equals/hashCode
        demonstrateEquality(customArray, arrayIndex);
    }

    private static void demonstrateArrayModification(CustomArray customArray, CustomArrayOperationImpl operations, int arrayIndex)
            throws CustomArrayException {
        logger.info("🔄 Demonstrating array modification...");

        CustomArray modifiedArray = operations.replaceCustomArrayElement(customArray, "MODIFIED", 1);
        logger.info("✅ Modified array: {}", getArrayPreview(modifiedArray.getMyArray()));

        String newMin = operations.findMinValue(modifiedArray);
        logger.info("📈 New min value after modification: '{}'", newMin);
    }

    private static void demonstrateEquality(CustomArray customArray, int arrayIndex) {
        CustomArray copyArray = CustomArray.newBuilder()
                .setMyArray(customArray.getMyArray())
                .build();

        boolean isEqual = customArray.equals(copyArray);
        logger.info("⚖️ Original equals copy: {}", isEqual);

        if (logger.isDebugEnabled()) {
            logger.debug("🔑 Original hashCode: {}, Copy hashCode: {}",
                    customArray.hashCode(), copyArray.hashCode());
        }
    }

    private static void performSortingOperations(CustomArray customArray, int arrayIndex) throws CustomArrayException {
        logger.info("🔢 === Sorting Operations for array {} ===", arrayIndex);
        CustomArraySortImpl sorter = new CustomArraySortImpl();

        // Selection Sort
        logger.info("1. Selection Sort");
        CustomArray selectionSorted = sorter.selectionSort(customArray);
        boolean isSelectionSorted = isSorted(selectionSorted.getMyArray());
        logger.info("   ✅ Sorted: {}, Verified: {}",
                getArrayPreview(selectionSorted.getMyArray()), isSelectionSorted);

        // Merge Sort
        logger.info("2. Merge Sort");
        CustomArray mergeSorted = sorter.mergeSort(customArray);
        boolean isMergeSorted = isSorted(mergeSorted.getMyArray());
        logger.info("   ✅ Sorted: {}, Verified: {}",
                getArrayPreview(mergeSorted.getMyArray()), isMergeSorted);

        // Quick Sort
        logger.info("3. Quick Sort");
        CustomArray quickSorted = sorter.quickSort(customArray);
        boolean isQuickSorted = isSorted(quickSorted.getMyArray());
        logger.info("   ✅ Sorted: {}, Verified: {}",
                getArrayPreview(quickSorted.getMyArray()), isQuickSorted);

        // Сравнение результатов
        compareSortResults(selectionSorted, mergeSorted, quickSorted, arrayIndex);
    }

    private static void compareSortResults(CustomArray selection, CustomArray merge, CustomArray quick, int arrayIndex) {
        String[] selectionArray = selection.getMyArray();
        String[] mergeArray = merge.getMyArray();
        String[] quickArray = quick.getMyArray();

        boolean allEqual = Arrays.equals(selectionArray, mergeArray) &&
                Arrays.equals(mergeArray, quickArray);

        logger.info("📋 Sort comparison for array {}: {}", arrayIndex,
                allEqual ? "✅ All algorithms produced identical results" : "⚠️ Results differ between algorithms");
    }

    private static void demonstrateWarehouseUsage(CustomArray customArray, int arrayIndex) {
        logger.info("🏪 === Warehouse Demo for array {} ===", arrayIndex);

        CustomArrayWarehouse warehouse = CustomArrayWarehouse.getInstance();
        var parameters = warehouse.getCustomArrayParametersMap();

        if (parameters.containsKey(customArray.getId())) {
            var storedParams = parameters.get(customArray.getId());
            logger.info("📦 Stored parameters for array {}:", arrayIndex);
            logger.info("   ID: {}, Min: '{}', Max: '{}'",
                    storedParams.customArrayId(), storedParams.minValue(), storedParams.maxValue());
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

    private static void handleApplicationError(CustomArrayException e) {
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