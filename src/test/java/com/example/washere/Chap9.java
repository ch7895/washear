package com.example.washere;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Slf4j
public class Chap9 {

    

    public static void main(String ... args){

        System.out.println("asdf");
        Runnable r1 = new Runnable() {
     
            public void run(){
                System.out.println("Hello1");
            }
        };
        
        Runnable r2 = () -> System.out.println("Hello2");

        r1.run();
        r2.run();

        
    }
}
