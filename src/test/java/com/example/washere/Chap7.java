package com.example.washere;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;

import org.junit.Test;

import java.util.stream.Stream;


@Slf4j
public class Chap7 {

    
    @Before //  
//    @BeforeClass // once 
    public void setup (){
        log.debug("start");
    
    }

    @Test 
    public void testStream1(){

        long startTime = System.currentTimeMillis();
        
        Long n= Stream.iterate(1L, i -> i + 1)
                         .limit(1000)
                         .reduce(0L, Long::sum);
        
        long endTime = System.currentTimeMillis();
        log.info("testStrema1 {},{}",endTime-startTime, Thread.currentThread().getStackTrace()[1].getMethodName());

        System.out.println(n);
    }


    @Test 
    public void testForLoop1(){

        long startTime = System.currentTimeMillis();
        
        int sum=0;
        for(int i=0; i<=1000; i++){
            sum+=i;
        }

        long endTime = System.currentTimeMillis();
        log.info("test1ForLoop1 {}",endTime-startTime);

        System.out.println(sum);
    }


    @Test 
    public void testStream2(){

        long startTime = System.currentTimeMillis();
        
        Long n= Stream.iterate(1L, i -> i + 1)
                         .limit(1000)
                         .parallel()
                         .reduce(0L, Long::sum);
        
        long endTime = System.currentTimeMillis();
        log.info("testStrema2 {}",endTime-startTime);

        System.out.println(n);
    }


}
