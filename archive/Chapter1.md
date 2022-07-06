# Chapter1

  

## 1.3 자바함수

- 프로그래밍 언어의 핵심은 값을 바꾸는 것
- JDK 8 이전 에서는 프로그램을 실행하는 동안 모든 구조체를 자유롭게 전달 불가했음.
- JDK 8 부터는 런타임에 메서드를 값으로 취급할 수 있게하여, 프로그래머들이 더 쉽게 프로그램을 구현 가능하도록 설계함.

    - 메서드 참조(Method reference) => :: 를 이용해서 직접 전달 가능  
    
    ```
    File[] hiddenFiles = new File(”.”).listFile(File::isHidden);
    
    ```
    
      
    
    - 람다(Lamda)  
    
    ```
    메서드 참조 : filterApples(inventory, Apple::isGreenApple);
    람다 : filterApples(inventory, (Apple a) => "GREEN".equals(a.getColor()) );
    ```
    
      
    
    > 한 번만 사용할 메서드는 따로 정의를 구현할 필요가 없지만,  
    > 람다가 몇 줄 이상으로 길어진다면 익명 람다보다는 메서드를 정의하고 메서드 참조를 활용하는 것이 바람직하다. **즉 코드의 명확성이 우선시 되어야 된다.**
    

⁠  

## 1.4 스트림 API(java.util.stream)

- 스트림 API를 통해 ‘컬렉션을 처리하면서 발생하는 모호함과 반복적인 코드 문제’ 및 ‘멀티코어 활용 어려움'의 문제를 해결하여 개발자가 더 쉽게 가공하여 사용 가능하도록 기능을 제공함.  
    \=> 멀티스레딩 모델 구현에 대한 고민을 할 필요가 없음.
- 외부 반복(external iteration) : for-each 루프 <> 내부 반복(internal iteration) : 스트림 API
- 데이터를 필터링(filtering), 추출(extracting), 그룹화(Grouping) 등의 기능 제공
- 순차처리 / 병렬처리를 쉽게 분류하여 사용 가능  
    
    ```java
    // 순차 처리 방식
    import static java.util.stream.Collectors.toList;
    List<Apple> heavyApples = inventory.stream().filter((Apple a) -> a.getWeight() > 120)
                                                .collect(toList());
    
    // 병렬 처리 방식
    import static java.util.stream.Collectors.toList;
    List<Apple> heavyApples = inventory.parallelStream().filter((Apple a) -> a.getWeight() > 120)
                                                .collect(toList());
    
    ```
    
      
    

  

## 1.5 디폴트 메서드

- 구현 클래스에서 구현하지 않아도 되는 메서드를 인터페이스에 추가할 수 있는 기능을 제공한다.
- 메서드 본문이 인터페이스의 일부로 포함된다.
- 디폴트 메서드를 이용하면 기존의 코드를 건드리지 않고도 원래의 인터페이스 설계를 자유롭게 확장이 가능하다.  
    **ex) java.util.List interface의 sort 메서드**  
    
    ```java
    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }
    
    ```
    
    ; JDK 8 이전에는 List를 구현하는 모든 클래스가 sort를 구현해야 했지만, JDK 8 이후부터는 디폴트 sort를 구현하지 않아도 된다.
    

  
  

## 1.6 함수형 프로그래밍에서 가져온 다른 유용한 아이디어

- JDK 8부터 NullPointer 예외를 피할 수 있도록 도와주는 Optional<T> 클래스를 제공함.
- 구조적 패턴 매칭 기법 지원(완벅하게는 미지원, 함수형 언어에서 다양한 데이터 형식을 switch에 사용할 수 있지만, 아직 Java에서는 문자열과 기본값만 제공함)