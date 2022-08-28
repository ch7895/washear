## Chapter6

## 6.1 collector

![collector](https://drek4537l1klr.cloudfront.net/urma2/Figures/06fig01_alt.jpg)

- 컬렉터는 스트림의 요소들 변환을 수행한다. 
- 스트림 그룹핑/파티셔닝을 수행한다. 
- 리듀싱과 summarizing을 수행한다. 

```java
List<Transaction> transactions = transactionStream.collect(Collectors.toList());
```

- 간결하고, 유연한 문법을 제공
- 스트림에서 'collect' 를 호출하면 내부적으로 reduction 수행
- Collectors 클래스는 많은 static factory method를 제공한다. 


## 6.2 reducing and summarizing 

```java
long howManyDishes = menu.stream().collect(Collectors.counting());
long howManyDishes = menu.stream().count();
``` 
다른 collector들과 조합할때 counting이 유용하다

#### 최소,최대값 찾기
```java
Comparator<Dish> dishCaloriesComparator =  Comparator.comparingInt(Dish::getCalories);
Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));
```

#### summarizition
 - Collectors 클래스는 summing 을 위한 함수륻 제공
```java
int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
```
- 스트림의 dish를 순회하며 calorie를 내부적으로 더함, 초기값은 0 설정
  (자매품 : Collectors.summingLong, Collectors.summingDouble)


- 통계 구하기
```java
IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
/**
* IntSummaryStatistics{count=9, sum=4300, min=120, average=477.777778, max=800}
*/
```

#### joining strings
```java
/**
 * 
 * /


