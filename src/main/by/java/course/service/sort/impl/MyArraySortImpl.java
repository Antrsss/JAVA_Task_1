package main.by.java.course.service.sort.impl;

import main.by.java.course.entity.MyArray;
import main.by.java.course.service.sort.MyArraySort;

public class MyArraySortImpl implements MyArraySort {

    @Override
    public MyArray selectionSort(MyArray myArray) {
        if (myArray == null || myArray.isEmpty()) {
            return myArray;
        }

        String[] array = myArray.getMyArray().clone(); // Работаем с копией

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;

            // Находим индекс минимального элемента в оставшейся части
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            // Меняем местами текущий элемент с минимальным
            if (minIndex != i) {
                String temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }

        return MyArray.newBuilder()
                .setMyArray(array)
                .build();
    }

    @Override
    public MyArray mergeSort(MyArray myArray) {
        if (myArray == null || myArray.isEmpty()) {
            return myArray;
        }

        String[] array = myArray.getMyArray().clone();
        mergeSort(array, 0, array.length - 1);

        return MyArray.newBuilder()
                .setMyArray(array)
                .build();
    }

    private void mergeSort(String[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Сортируем левую и правую половины
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            // Сливаем отсортированные половины
            merge(array, left, mid, right);
        }
    }

    private void merge(String[] array, int left, int mid, int right) {
        // Создаем временные массивы для левой и правой половин
        String[] leftArray = new String[mid - left + 1];
        String[] rightArray = new String[right - mid];

        // Копируем данные во временные массивы
        System.arraycopy(array, left, leftArray, 0, leftArray.length);
        System.arraycopy(array, mid + 1, rightArray, 0, rightArray.length);

        // Сливаем временные массивы обратно в основной
        int i = 0, j = 0, k = left;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i].compareTo(rightArray[j]) <= 0) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Копируем оставшиеся элементы левой половины
        while (i < leftArray.length) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        // Копируем оставшиеся элементы правой половины
        while (j < rightArray.length) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    @Override
    public MyArray quickSort(MyArray myArray) {
        if (myArray == null || myArray.isEmpty()) {
            return myArray;
        }

        String[] array = myArray.getMyArray().clone();
        quickSort(array, 0, array.length - 1);

        return MyArray.newBuilder()
                .setMyArray(array)
                .build();
    }

    private void quickSort(String[] array, int low, int high) {
        if (low < high) {
            // Разделяем массив и получаем индекс опорного элемента
            int pivotIndex = partition(array, low, high);

            // Рекурсивно сортируем элементы до и после опорного
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private int partition(String[] array, int low, int high) {
        // Выбираем последний элемент как опорный
        String pivot = array[high];
        int i = low - 1; // Индекс меньшего элемента

        for (int j = low; j < high; j++) {
            // Если текущий элемент меньше или равен опорному
            if (array[j].compareTo(pivot) <= 0) {
                i++;

                // Меняем элементы местами
                String temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // Помещаем опорный элемент на правильную позицию
        String temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }

    // Дополнительные методы для разных стратегий выбора опорного элемента

    public MyArray quickSortWithFirstPivot(MyArray myArray) {
        if (myArray == null || myArray.isEmpty()) {
            return myArray;
        }

        String[] array = myArray.getMyArray().clone();
        quickSortWithFirstPivot(array, 0, array.length - 1);

        return MyArray.newBuilder()
                .setMyArray(array)
                .build();
    }

    private void quickSortWithFirstPivot(String[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionWithFirstPivot(array, low, high);
            quickSortWithFirstPivot(array, low, pivotIndex - 1);
            quickSortWithFirstPivot(array, pivotIndex + 1, high);
        }
    }

    private int partitionWithFirstPivot(String[] array, int low, int high) {
        // Выбираем первый элемент как опорный
        String pivot = array[low];
        int i = low;

        for (int j = low + 1; j <= high; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                String temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        String temp = array[i];
        array[i] = array[low];
        array[low] = temp;

        return i;
    }
}