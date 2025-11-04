package by.zgirskaya.course.entity;

public record CustomArrayParameters(
        int customArrayId,
        String minValue,
        String maxValue,
        double averageValue,
        int arraySum,
        int positiveValuesCount,
        int negativeValuesCount
) {
}
