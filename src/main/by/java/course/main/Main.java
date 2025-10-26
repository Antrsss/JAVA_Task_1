package main.by.java.course.main;

import main.by.java.course.creator.ArrayCreator;
import main.by.java.course.entity.MyArray;
import main.by.java.course.reader.ArrayReader;
import main.by.java.course.validator.ArrayValidator;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] strings = ArrayReader.readArray(System.in);
        List<String> validStrings = ArrayValidator.filterValidStrings(strings);

        MyArray array = MyArray.newBuilder()
                .setMyArray(strings)
                .build();

        String[] stringArray = ArrayCreator.factoryArray(validStrings);
    }
}