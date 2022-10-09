package com.example.washere.chap9;

public class NYTimes implements Observer{
    
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("money")){
            System.out.println("Breaking news in NY!" + tweet);
        }
    }
}


