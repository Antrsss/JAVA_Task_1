package main.by.java.course.entity;

public record MyArrayParameters(
        int myArrayId,
        String minValue,
        String maxValue,
        double averageValue,
        int arraySum,
        int positiveValuesCount,
        int negativeValuesCount
) {
}
