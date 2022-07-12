## Chapter4

## 

## 4.1 stream?

- 선언형으로 컬렉션 데이터를 다룰수 있다?
- 복잡한 멀티스레드 코드대신 투명하고 간단하게 처리
- 요리중 칼로리가 400이하인 요리, 이름순으로 정렬
**before**
```java
List<Dish> lowCaloricDishes = new ArrayList<>();
for(Dish dish: menu) {
    if(dish.getCalories() < 400) {
        lowCaloricDishes.add(dish);
    }
}
Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
    public int compare(Dish dish1, Dish dish2) {
        return Integer.compare(dish1.getCalories(), dish2.getCalories());
    }
});
List<String> lowCaloricDishesName = new ArrayList<>();
for(Dish dish: lowCaloricDishes) {
    lowCaloricDishesName.add(dish.getName());
}
```
**after**
```java
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
List<String> lowCaloricDishesName =
               menu.stream()
                   .filter(d -> d.getCalories() < 400)
                   .sorted(comparing(Dish::getCalories))
                   .map(Dish::getName)
                   .collect(toList());


```

**스트림특징**
> 선언형 / 조립가능 / 병렬화 


## 4.2 Getting started with streams

스트림이란 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소
- 연속된 요소 
- 소스 : 컬렉션, 배열, i/o 등의 자원에서 만들어짐
- 데이터처리연산 : 함수평프로그래밍, DB연산 지원, 순차적/병렬적

스트림특징
- 파이프라인 : **lazy** 처리, short-circuit
- 내부반복 


## 4.3 stream vs collection

- 컬렉션 : dvd 영화, 
- 스트림 : 스트리밍 영화

스트림특징
- 딱 1번만 사용
- 내부반복 
- 중개함수, 종단함수




