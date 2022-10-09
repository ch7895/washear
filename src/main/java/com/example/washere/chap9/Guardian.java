package com.example.washere.chap9;

public class Guardian implements Observer{

    public void notify (String tweet){
        System.out.println("guardian");
        if(tweet != null && tweet.contains("queen")) {
            System.out.println("Yet more news from London..." + tweet);
          }
    }

}
