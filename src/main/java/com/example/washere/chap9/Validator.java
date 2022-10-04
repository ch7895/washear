package com.example.washere.chap9;

public class Validator {

    private final ValidationStrategy validationStrategy;

    public Validator (ValidationStrategy v){
        this.validationStrategy=v;
    }
    
    public boolean validate(String s){
        return validationStrategy.execute(s);
    }


    public static void main (String... args){
        Validator numericValidator = new Validator(new IsNumeric());
        boolean b1 = numericValidator.validate("111");

        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean b2 = lowerCaseValidator.validate("bbbb");

        System.out.println(b1 + "/" + b2);


        Validator lnumericValidator = new Validator((String s) -> s.matches("\\d+"));
        boolean lb1 = lnumericValidator.validate("111");

        Validator llowerCaseValidator = new Validator((String s) -> s.matches("[a-z]+"));
        boolean lb2 = llowerCaseValidator.validate("bbbb");

        System.out.println(lb1 + "/" + lb2);


    }
    
}
