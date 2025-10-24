package main;

import creator.ArrayCreator;
import reader.ArrayReader;
import validator.ArrayValidator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] strings = ArrayReader.readArray(System.in);
        List<String> validStrings = ArrayValidator.filterValidStrings(strings);

        String[] stringArray = ArrayCreator.factoryArray(validStrings);
        ArrayCreator.showArray(stringArray);
    }
}