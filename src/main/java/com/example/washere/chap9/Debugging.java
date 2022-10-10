package com.example.washere.chap9;

import java.util.*;

import org.springframework.data.geo.Point;

public class Debugging {
    /*
    public static void main(String... args){
        List<Point> points = Arrays.asList(new Point(12,2), null);
        points.stream().map(p->p.getX()).forEach(System.out::println);
    }
    */
    public static void main(String... args){

    List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers.stream().map(Debugging::divideByZero).forEach(System
         .out::println);
    }
    public static int divideByZero(int n){
        return n / 0;
    }

    
}
