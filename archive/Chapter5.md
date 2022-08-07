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