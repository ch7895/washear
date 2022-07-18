package com.example.washere.chap4.chkyu;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import static java.util.stream.Collectors.toList;

@Slf4j
public class Chap5 {

    private List<Dish> menu = new ArrayList<>();
    
    final int testN = 20;

    @Before //  
//    @BeforeClass // once 
    public void setup (){
        log.debug("start");

        // Dish dish2 = Dish.builder().calories(2000).name("dish2").build();
        // Dish dish1 = Dish.builder().calories(1000).name("dish1").build();

        // menu.add(dish1);
        // menu.add(dish2);

        for(int i=0; i<testN; i++){
        
            SplittableRandom splittableRandom = new SplittableRandom();
            
            int r = splittableRandom.nextInt(1, 1000);
            menu.add(Dish.builder().calories(r).name("dish"+r).build());
        }

    }

    @Test 
    public void test1(){

        //Stream s = Stream.of(menu);
        log.debug("test1");

        List<String> threeHighCaloricDishNames =
                menu.stream()
                        .filter(dish -> dish.getCalories() > 300)
                        .map(Dish::getName)
                        .collect(toList());

        threeHighCaloricDishNames.forEach(System.out::println);
        
    }


    @Test public void test2(){

        log.debug("test2");


        List<String> result = new ArrayList<>();

        
        for(Dish d : menu){
            if(d.getCalories() > 300){
                result.add(d.getName());
            }
        }
        

        log.debug(result.size()+"");
        for(int i=0; i<result.size(); i++){
            System.out.println(result.get(i));
        }
    }

    @After
    public void done(){
        log.debug("done");
    }

    @Data
    @Builder
    static class Dish {
        int calories;
        String name;
    }
}
