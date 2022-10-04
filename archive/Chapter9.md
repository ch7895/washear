## Chapter9

## 9.1 Refactoring for improved readability and flexibillity

### improving code readabillity

- 좋은가독성이란? 다른사람의 코드가 얼마나 쉽게 이해되는가
- 익명클래스 -> 람다
- 람다 -> 메소드 참조
- 명령형 데이터 처리 -> 스트림

### 익명클래스
- 'this', 'super'가 익명클래스와 람다에서 서로 다르다. 
- 'this'는 익명클래스에서는 자신을 가르키지만, 람다에서는 자신을 감싸는 클래스를 가리킴
- 익명 클래스는 감싸고 있는 클래스의 변수를 가릴수 있으나 람다에서는 불가능하다

```java
    Runnable r1 = new Runnable() {
        public void run(){
            System.out.println("Hello");
        }
    };

    Runnable r2 = () -> System.out.println("Hello");

```


### 메소드 참조 
- 람다식을 메서드 참조로 변환해서 가독성을 높일 수도 있다. 
```java
    Map<CaloricLevel, List<Dish>> dishesByCaloricLevel =
            menu.stream()
                .collect(
                    groupingBy(dish -> {
                        if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                        else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                        else return CaloricLevel.FAT;
    }));

    Map<CaloricLevel, List<Dish>> dishesByCaloricLevel =
            menu.stream().collect(groupingBy(Dish::getCaloricLevel));

```

### 명령형 데이터 처리

- 스트림 api 는 더이터 처리 파이프라인의 의도를 더욱 명확하게 표현한다. 
- 스트림은 멀티코어 아키텍처를 활용할 수 있다. 
- 스트림으로 변환 tool https://ieeexplore.ieee.org/document/6606699


```java
    List<String> dishNames = new ArrayList<>();
        for(Dish dish: menu) {
            if(dish.getCalories() > 300){
                dishNames.add(dish.getName());
        }
    }

    menu.parallelStream()
        .filter(d -> d.getCalories() > 300)
        .map(Dish::getName)
        .collect(toList());

```

### 유연성 개선
```java
     if (logger.isLoggable(Log.FINER)) {
            logger.finer("Problem: " + generateDiagnostic());
}
```
- logger의 상태가 isLoggable메서드에의해 클라이언트 코드로 노출된다. 
- 로깅할때마다 logger의 상태를 확인해야 한다. 

```java
    logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());
```
- log 메소드는 내부적으로 실행된다. logger가 finer 레벨일때 람다는 메세지를 전달한다. 


## 9.2 객체지향패턴을 람다를 통한 리팩토링

### 전략패턴 

- 하나의 인터페이스와 여러개의 구현체 
- 하나이상의 client가 전략을 결정

```java

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
```
