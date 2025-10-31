package main.by.java.course.observer;

public interface MyArrayObservable {
    void attach(MyArrayObserver observer);
    void detach(MyArrayObserver observer);

    void notifyObservers();
}
