package com.example.washere.chap9;

import java.util.ArrayList;
import java.util.List;

public class Feed implements Subject {
    
    private final List<Observer> observers = new ArrayList<>();
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }
    public void notifyObservers(String tweet) {
        observers.forEach(o -> o.notify(tweet));

    }

    public static void main(String... args){

        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());

        f.notifyObservers("queen money");
        System.out.println("asdf");

    }
}
