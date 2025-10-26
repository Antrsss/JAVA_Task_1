package main.by.java.course.creator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ArrayCreator {
    private static Logger logger = LogManager.getLogger();
    private static String[] array;

    public static String[] factoryArray(List<String> strings) {
        String[] result = new String[strings.size()];

        for (int i = 0; i < strings.size(); i++) {
            result[i] = strings.get(i);
        }

        return result;
    }
}
