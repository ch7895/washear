package com.example.washere;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
public class Chap4 {

    private List<Dish> menu = new ArrayList<>();

    @Before
    public void setup (){
        log.debug("start");

        Dish dish2 = Dish.builder().calories(2000).name("dish2").build();
        Dish dish1 = Dish.builder().calories(1000).name("dish1").build();

        menu.add(dish1);
        menu.add(dish2);
    }

    @Test public void test1(){

        //Stream s = Stream.of(menu);
        log.debug("test1");

        List<String> threeHighCaloricDishNames =
                menu.stream()
                        .filter(dish -> dish.getCalories() > 300)
                        .map(Dish::getName)
                        .limit(3)
                        .collect(toList());
        System.out.println(threeHighCaloricDishNames);
    }

    @After public void done(){
        log.debug("done");
    }

    @Data
    @Builder
    static class Dish {
        int calories;
        String name;
    }
}
