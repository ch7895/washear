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
/**
* IntSummaryStatistics{count=9, sum=4300, min=120, average=477.777778, max=800}
*/
IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
```

#### joining strings
joining은 내부적으로 stringbuilder를 사용한다. 
```java
String shortMenu = menu.stream().collect(joining());
String shortMenu = menu.stream().map(Dish::getName).collect(joining(","));
```

#### Generalized summarization with reduction
collector는 reducing으로 대체가 가능하다
```java
int totalCalories = menu.stream().collect(reducing( 0, Dish::getCalories, (i, j) -> i + j));
// 1:시작값 이자 반환값 , 2:변환함수 , 3: 집계함수
```
가장 높은 칼로리 계싼
```java
Optional<Dish> mostCalorieDish =
    menu.stream().collect(reducing(
        (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
```



## 6.3 Grouping
- type에 따라 각 list로 group화 시켜 Map으로 변환
- dish::getType을 classification funciton 
```java
Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
//This will result in the following Map:
//{FISH=[prawns, salmon], OTHER=[french fries, rice, season fruit, pizza],
// MEAT=[pork, beef, chicken]}
```
- classification 함수를 직접 구현
```java
public enum CaloricLevel { DIET, NORMAL, FAT }
Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
groupingBy(dish -> {
          if (dish.getCalories() <= 400) return CaloricLevel.DIET;
          else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL; 
          else return CaloricLevel.FAT;
} ));
```

#### manipulation grouped elements
- group 하면서 filter
```java
 Map<Dish.Type, List<Dish>> caloricDishesByType =
     menu.stream().filter(dish ->  dish.getCalories() > 500)
                                  .collect(groupingBy(Dish::getType));
//{OTHER=[french fries, pizza], MEAT=[pork, beef], FISH=[]}
```
- flatmap 활용
```java
Map<String, List<String>> dishTags = new HashMap<>();
dishTags.put("pork", asList("greasy", "salty"));
dishTags.put("beef", asList("salty", "roasted"));
dishTags.put("chicken", asList("fried", "crisp"));
dishTags.put("french fries", asList("greasy", "fried"));
dishTags.put("rice", asList("light", "natural"));
dishTags.put("season fruit", asList("fresh", "natural"));
dishTags.put("pizza", asList("tasty", "salty"));
dishTags.put("prawns", asList("tasty", "roasted"));
dishTags.put("salmon", asList("delicious", "fresh"));


Map<Dish.Type, Set<String>> dishNamesByType =
   menu.stream()
      .collect(groupingBy(Dish::getType,
               flatMapping(dish -> dishTags.get( dish.getName() ).stream(),
toSet())));
// {MEAT=[salty, greasy, roasted, fried, crisp], FISH=[roasted, tasty, fresh,
//             delicious], OTHER=[salty, greasy, natural, light, tasty, fresh, fried]}
```


#### multilevel grouping 
일반적으로 groupingBy 함수의 2번째 인자에 리듀싱 연산을 수행할 파라미터를 전달

`
Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(groupingBy(Dish::getType, ummingInt(Dish::getCalories)));
`

```java
Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
     menu.stream().collect
          ( groupingBy(Dish::getType,
               groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL; 
                    else return CaloricLevel.FAT;
               }
          ))
//{MEAT={DIET=[chicken], NORMAL=[beef], FAT=[pork]},
// FISH={DIET=[prawns], NORMAL=[salmon]},
// OTHER={DIET=[rice, seasonal fruit], NORMAL=[french fries, pizza]}}
```

#### collecting data in subgroup
```java
// select count(*)
Map<Dish.Type, Long> typesCount = menu.stream().collect(groupingBy(Dish::getType, counting()));
// The result is the following Map: {MEAT=3, FISH=2, OTHER=4}

// select max(*)
Map<Dish.Type, Optional<Dish>> mostCaloricByType =
    menu.stream()
        .collect(groupingBy(Dish::getType,
                            maxBy(comparingInt(Dish::getCalories))));
//{FISH=Optional[salmon], OTHER=Optional[pizza], MEAT=Optional[pork]}
```


## 6.4 partitioning
분류함수로 predicate 를 사용, key가 boolen인 결과를 반환
```java
Map<Boolean, List<Dish>> partitionedMenu =
             menu.stream().collect(partitioningBy(Dish::isVegetarian));
//{false=[pork, beef, chicken, prawns, salmon], true=[french fries, rice, season fruit, pizza]}
```

#### Advangeage of partitioning 
True, false 여부와 각 리스트를 유지하는 것이 장점
group by 와 같이 사용할 수도 있다. 
```java
Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
     menu.stream().collect(
          partitioningBy(Dish::isVegetarian,
               groupingBy(Dish::getType)));
//{false={FISH=[prawns, salmon], MEAT=[pork, beef, chicken]},
 //true={OTHER=[french fries, rice, season fruit, pizza]}}
```


