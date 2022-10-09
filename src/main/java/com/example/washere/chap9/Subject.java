package com.example.washere.chap9;

public interface Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}
