package com.example.washere;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.example.washere.chap9.HeaderTextProcessing;
import com.example.washere.chap9.ProcessingObject;
import com.example.washere.chap9.SpellCheckerProcessing;

@Slf4j
public class Chap9 {

    
    @Test 
    public void testChainOfResponsibillity(){

        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();

        p1.setSuccessor(p2);
        //labdas -> lambda
        String result = p1.handle("Aren't labdas really sexy?!");
        log.info("testChainOfResponsibillity:{}", result);

        UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan : " + text;
        UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result1 = pipeline.apply("Aren't labdas really sexy?!");
        log.info("testChainOfResponsibillity:{}", result1);
    
    }


}
