package com.example.washere.chap9;

public class IsNumeric implements ValidationStrategy {

    public boolean execute (String s){
        return s.matches("\\d+");
    }
}