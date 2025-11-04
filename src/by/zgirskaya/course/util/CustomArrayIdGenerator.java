package by.zgirskaya.course.util;

public class CustomArrayIdGenerator {
    private static int idCounter = 0;
    public static int nextId() { return ++idCounter; }

    private CustomArrayIdGenerator() {}
}
