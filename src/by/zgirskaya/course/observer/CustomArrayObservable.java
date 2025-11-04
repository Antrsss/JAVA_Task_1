package by.zgirskaya.course.observer;

public interface CustomArrayObservable {
    void attach(CustomArrayObserver observer);
    void detach(CustomArrayObserver observer);

    void notifyObservers();
}
