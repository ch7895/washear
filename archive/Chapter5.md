## Chapter4

## 

## 5.1 filtering

- predicate(boolean을 반환하는 함수) 를 파라미터로 사용
- distinct (hashcode, equals)
![distinc](https://drek4537l1klr.cloudfront.net/urma2/Figures/05fig02_alt.jpg)

## 5.2 slicing 

false 시점 으로 구분
- takeWhile : false 이전
- dropWhile : false 이후
```java
List<Dish> filteredMenu
    = specialMenu.stream()
                 .filter(dish -> dish.getCalories() < 320)
                 .collect(toList());
```

limit, skip 
- limit : 앞에서부터 n개 까지
- skip : 앞에서부터 n개 제외 

상호보완해서 사용 고려

## 5.3 mapping 

스트림으로부터 일부정보들을 활용하여 새로운 스트림을 생성
- map, 
- flatMap

```java
words.stream()
     .map(word -> word.split(""))
     .distinct()
     .collect(toList());
```

![flatmap](https://drek4537l1klr.cloudfront.net/urma/Figures/05fig06_alt.jpg)


## 5.4 finding and matching

스트림이 predicate 조건을 만족하는지 조사
결과값은 boolean

- anyMatch : true가 1개이상
- allMatch : 모두 true
- noneMatch : 모두 false

     ```java
     if(menu.stream().anyMatch(Dish::isVegetarian)) {
          System.out.println("The menu is (somewhat) vegetarian friendly!!");
     }
     ```

### find

- findAny, findFirst
- 현재 스트림의 1개 요소를 반환, 
- 차이 : 순서보장(?)

     ```java
     List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
     Optional<Integer> firstSquareDivisibleByThree =
          someNumbers.stream()
                       .map(n -> n * n)
                       .filter(n -> n % 3 == 0)
                       .findFirst(); // 9
     ```


## 5.5 reducing

- 함수를 사용해서 스트림 

- sum 

     ```java
     int sum = 0;
     for (int x : numbers ) {
          sum += x;
     }

     ...
     int sum = numbers.stream().recude(0, a(,b) -> a+b)

     int sum = numbers.parallelStream().reduce(0, Integer::sum);

     ```


- 스트림 중개함수/종단함수
![operations](https://i0.wp.com/javaconceptoftheday.com/wp-content/uploads/2020/01/Java8StreamIntermediateVsTerminalOperations.png?w=626&ssl=1)



## 5.8 Building Stremas

- Stream.of
     ```java
     Stream<String> stream = Stream.of("Modern ", "Java ", "In ", "Action");
     ```

- Arrays.stream()
     ```java
     int[] numbers = {2, 3, 5, 7, 11, 13};
     int sum = Arrays.stream(numbers).sum();
     ```

- infinite stream
     ```java
     Stream.iterate(0, n -> n + 2)
          .limit(10)
          .forEach(System.out::println);
     ```
