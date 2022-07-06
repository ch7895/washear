## Chapter3

## 

## 3.1 람다표현식

- 람다표현식은 메서드로 전달 할 수 있는 익명 함수를 단순화한 것
- 익명클래스를 생성하여 전달하는 과정은 자질구레한 코드가 많이 생긴다. 람다를 이용하면 간결한 방식으로 코드를 전달 할 수 있다.
- 파라미터, 화살표, 바디로 이루어진다.

> **(Apple a1, Apple a2)**       **->**        **a1.getWeight().compareTo(a2.getWeight());**  
> **\[ 파라미터 \]             \[화살표\]           \[바디\]**

## 3.2 람다를 어디서 사용할까?

## 3.2.1 함수형 인터페이스

- 함수형 인터페이스는 정확히 하나의 추상 메서드를 지정하는 인터페이스다.  
    ex) 자바 API의 Comparator, Runnable 등등
- 인터페이스는 디폴터 메서드(=인터페이스의 메서드를 구현하지 않은 클래스를 고려해서 기본 구현을 제공하는 바디를 포함하는 메서드)를 포함할 수 있다.  
    많은 디폴트 메서드가 있더라도 **추상 메서드가 오직 하나면** 함수형 인터페이스이다.
- 함수형 인터페이스로 뭘 할 수 있는지???  
    \=> 람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있어 전체 표현식을 함수형 인터페이스의 인스턴스로 취급(함수형 인터페이스를 구현한 클래스의 인스턴스화)할 수 있다.

## 3.2.2 함수 디스크립터

- 람다 표현식의 시그니처를 서술하는 메서드를 **함수 디스크립터**function descriptor라고 부른다.

- Runnable 인터페이스의 유일한 추상 메서드 run은 인수와 반환값이 없으므로(void 반환) Runnable 인터페이스는 인수와 반환값이 없는 시그니처로 정의 가능하다.  
  
  ```java
  @FunctionalInterface
  public interface Runnable {
      public abstract void run();
  }
  ```

## 3.3 람다 활용 : 실행 어라운드 패턴

- 1단계 : 기본코드  
  
  ```java
  public String processFile() throws IOException {
     try(BufferedReader br = new BufferedReader(new FileReader("/Users/kyongsu/spring-study/modern-java/action/src/test/java/modern/java/action/chapter3/data.txt"))){
        return br.readLine();
     }
  }
  
  @Test
  void 테스트(){
     try {
        String data = processFile();
        log.info("data={}", data);
     } catch (IOException e) {
        e.printStackTrace();
     }
  }
  ```

- 2단계 : 함수형 인터페이스를 이용해서 동작 전달  
  
  ```java
  @FunctionalInterface
  public interface BufferedReaderProcessor {
      String process(BufferedReader b) throws IOException;
  }
  
  public String processFile(BufferedReaderProcessor p) throws IOException {
     try(BufferedReader br = new BufferedReader(new FileReader("/Users/kyongsu/spring-study/modern-java/action/src/test/java/modern/java/action/chapter3/data.txt"))){
         return p.process(br);
     }
  }
  
  @Test
  void execute(){
     try {
        String result = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        log.info("result={}", result);
     } catch (IOException e) {
        e.printStackTrace();
     }
  }
  ```

- 3단계 : 람다 전달  
  
  ```java
  @Test
  void lamda_execute(){
     try {
        String oneLine = processFile((BufferedReader br) -> br.readLine());
        String twoLine = processFile((BufferedReader br) -> br.readLine()+ "\r\n" +br.readLine());
  
        log.info("oneLine={}", oneLine);
        log.info("twoLine={}", twoLine);
     } catch (IOException e) {
        e.printStackTrace();
     }
  }
  ```

## 3.4 함수형 인터페이스 사용

- java.util.function 패키지에서 제공하는 함수형 인터페이스  
  
  |                    |                  |                                                                                                                                                                                                                                                                                                 |
  | ------------------ | ---------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
  | 함수형 인터페이스          | 함수 디스크립터         | 기본형 특화                                                                                                                                                                                                                                                                                          |
  | Predicate<T>       | T → boolean      | IntPredicate, LongPredicvate,   <br>DoublePredicate                                                                                                                                                                                                                                             |
  | Consumer<T>        | T → void         | IntConsumer, LongConsumer,  <br>DoubleConsumer                                                                                                                                                                                                                                                  |
  | Function<T, R>     | T → R            | IntFunction<R>,  <br>IntToDoubleFunction,  <br>IntToLongFunction,  <br>LongFunction<R>,  <br>LongToDoubleFunction,  <br>LongToIntFunction,  <br>DoubleFunction<R>,  <br>DoubleToIntFunction,  <br>DoubleToLongFunction,  <br>ToIntFunction<T>,  <br>ToDoubleFunction<T>,  <br>ToLongFunction<T> |
  | Supplier<T>        | () → T           | BooleanSupplier, IntSupplier,  <br>LongSupplier, DoubleSupplier                                                                                                                                                                                                                                 |
  | UnaryOperator<T>   | T → T            | IntUnaryOperator,  <br>LongUnaryOperator,  <br>DoubleUnaryOperator                                                                                                                                                                                                                              |
  | BinaryOperator<T>  | (T, T) → T       | IntBinaryOperator,  <br>LongBinaryOperator,  <br>DoubleBinaryOperator                                                                                                                                                                                                                           |
  | BiPredicate<L, R>  | (T, U) → boolean |                                                                                                                                                                                                                                                                                                 |
  | BiConsumer<T, U>   | (T, U) → void    | ObjIntConsumer<T>,  <br>ObjLongConsumer<T>,  <br>ObjDoubleConsumer<T>                                                                                                                                                                                                                           |
  | BiFuction<T, U, R> | (T, U) → R       | ToIntBiFunction<T, U>,  <br>ToLongBiFunction<T, U>,  <br>ToDoubleBiFunction<T, U>                                                                                                                                                                                                               |

- 람다와 함수형 인터페이스 예제  
  
  |            |                                                                   |                                                                         |
  | ---------- | ----------------------------------------------------------------- | ----------------------------------------------------------------------- |
  | 사용 사례      | 람다 예제                                                             | 대응하는 함수형 인터페이스                                                          |
  | 불리언 표현     | (List<String> list) -> list.isEmpty()                             | Predicate<List<String>>                                                 |
  | 객체 생성      | () -> new Apple(10)                                               | Supplier<Apple>                                                         |
  | 객체에서 소비    | (Apple a) -> System.out.println(a.getWeight())                    |                                                                         |
  | 객체에서 선택/추출 | (String s) -> s.length()                                          | Function<String, Integer> 또는  <br>ToIntFunction<String>                 |
  | 두 값 조합     | (int a, int b) -> a \* b                                          | IntBinaryOperator                                                       |
  | 두 객체 비교    | (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeitght()) | BiFunction<Apple, Apple, Integer> 또는  <br>ToIntBiFunction<Apple, Apple> |

## 3.5 형식 검사, 형식 추론, 제약

## 3.6 메서드 참조

- 메서드 참조를 사용하면 코드 가독성을 높일 수 있다. (사용문법 **::**)

- 메서드 참조 사용 방법
  
  - 정적 메서드 참조  

        - Interger의 parseInt => **Integer::parseInt**
        - (args) -> ClassName.staticMethod(args) => **(args) -> ClassName::staticMethod**
    
    - 다양한 형식의 인스턴스 메서드 참조
    
        - String의 length => **String::length**
        - (arg0, rest) -> arg0.instanceMethod(rest) => **(arg0, rest) -> ClassName::instanceMethod**
    
    - 기존 객체의 인스턴스 메서드 참조
    
        - Transacation 객체를 할당받은 지역변수 exTranscation이 있고, getValue 메서드가 있으면 **exTranscation::getValue**
        - (args) -> expr.instanceMethod(args) => **expr::instanceMethod**

-