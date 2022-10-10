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

### template method

- 전체적인 알고리즘의 개요는 정해져 있고, 일부 로직만 유연하게 사용하고 싶을때

```java

    abstract class OnlineBanking {
        public void processCustomer(int id){
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy(c);
        }
        abstract void makeCustomerHappy(Customer c);
    }

     //Consumer<Customer> 파라미터 추가
    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
                Customer c = Database.getCustomerWithId(id);
                makeCustomerHappy.accept(c);
    }

    new OnlineBankingLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello " + c.getName());
```

### Observer

- 어떤 이벤트가 발생했때 한 객체가 다른객체리스트에 알림을 보낼 수 있는 패턴
- 어떤 주식의 가격이 변경이 되었을때 트레이더들이 알고 싶을때
```java
    interface Observer {
    void notify(String tweet);
    }

    Class NYTimes implements Observer {
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("money")) {
        System.out.println("Breaking news in NY!" + tweet);
        }
    }
    }

    Class Guardian implements Observer {
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("queen")) {
        System.out.println("Yet more news from London..." + tweet);
        }
    }
    }

    //2. 주제
    interface Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
    }

    Class Feed implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    public void registerObserver(Observer o) {
        this.observers.add(0);
    }
    public void notifyServeres(String tweet) {
        observers.forEach(o -> o.notify(tweet));
    }
    }

    Feed f = new Feed();
    f.registerObserver(new NYTimes());
    f.registerObserver(new Guardian());
    f.notifyServeres("The queen said her favorite book is Modern Java in Action!");
```

lambda
```java
    // 각 옵저버들의 notify를 구현하지 말고 함수를 파라미터로 전달
    f.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("money")){
                System.out.println("Breaking news in NY! " + tweet);
            }
    });
    f.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("queen")){
                System.out.println("Yet more news from London... " + tweet);
    }
    });
```

### 의무체인
- 작업 처리 객체의 체인을 만들때 의무 체인패턴
- 보통 추상클래스로 구현되며 작업이 끝나면 후임자에게 넘겨줌
```java
    public abstract class processingObject<T> {
    protected ProcessingObject<T> successor;
    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }
    public T handle(T input) {
        T r = handleWork(input);
        if(successor != null) {
        return successor.handle(r);
        }
        return r;
    }
    abstract protected T handleWork(T input);
    }

    public class HeaderTextProcessing extends ProcessingObject<String> {
        public String handleWork(String text) {
            return "From Raoul, Mario and Alan : " + text;
        }
    }

    public class SpellCheckerProcessing extends ProcessingObject<String> {
        public String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }

    ProcessingObject<String> p1 = new HeaderTextProcessing();
    ProcessingObject<String> p2 = new SpellCheckerProcessing();
    p1.setSuccessor(p2); // chaining tow processing object
    String result = p1.handle("Aren't labdas really sexy?!");
    System.out.println(result);

    //lambda 
    UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan : " + text;
    UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
    Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
    String result = pipeline.apply("Aren't labdas really sexy?!");

```

#### UnaryOperator
- Type T의 인자를 하나 받고, 동일한 Type T객체를 리턴하는 함수형 인터페이스
- Function 을 상속하며 apply() 호출하여 작업수행
- func1.andThen(func2) -> apply() 호출시 func1의 결과가 func2의 인자로 전달, func2의 결과값을 리턴


### factory 
- 인스턴스화 로직을 노출하지 않고 객체를 생성할때
- 팩토리클래스, 인스턴스 클래스 
```java
    public class ProdectFactor {
        public static Product createProduct(String name) {
            switch(name) {
            case "loan" : return new Loan();
            case "stock" : return new Stock();
            case "bond" : return new Bond();
            default : throw new RuntimeException("No Such product" + name);
            }
        }
    }

    product p = ProductFactory.createProduct("loan");

    //lambda
    메서드 참조
    Supplier<Product> loanSupplier = Loan::new;
    Loan loan = loanSupplier.get();

    //상품명과 생성자를 연결하는 Map 코드로 재구현
    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }


    //Map을 이용해 다양한 상품을 인스턴스화
    public static Product createProduct(String name) {
        Supplier<product> p = map.get(name);
        if(p != null) return p.get();
        throw new RuntimeException("No Such product" + name);
    }

```

#### supplier 
- T타입을 반환하는 함수를 정의하고, get 메서드를 통해 결과 리턴하는 메서드 하나만 가지고 있음

### 9.3 Testing lambdas

- 보통 좋은 소프트웨어 엔지니어 연습은 단위테스트로 프로그램의 행동을 보장한다. 
```java
    public class Point {
        private final int x;
        private final int y;
        private Point(int x, int y) {
            this.x = x;
            this.y = y; 
        }
        
        public int getX() { return x; }
        public int getY() { return y; }
        public Point moveRightBy(int x) {
            return new Point(this.x + x, this.y);
        }
    }

    //test moveRightBy 
    @Test
    public void testMoveRightBy() throws Exception {
        Point p1 = new Point(5, 5);
        Point p2 = p1.moveRightBy(10);
        assertEquals(15, p2.getX());
        assertEquals(5, p2.getY());
    }
```
람다의 경우 이름을 가지고 있지 않다(익명함수), 그래서 이름으로 테스트를 하기 힘들다. 
대신 람다를 필드에 저장해서 재사용할 수 있으며 로직을 테스트 할 수 있다. 
람다는 함수형 인터페이스의 인스턴스를 생성한다. 그 인스턴스를 테스트 할 수 있다. 

```java
    public class Point {
        public final static Comparator<Point> compareByXAndThenY = comparing(Point::getX).thenComparing(Point::getY);
            ...
    }

    @Test
    public void testComparingTwoPoints() throws Exception {
        Point p1 = new Point(10, 15);
        Point p2 = new Point(10, 20);
        int result = Point.compareByXAndThenY.compare(p1 , p2);
            assertTrue(result < 0);
    }
```

### 람다를 사용하는 메서드

람다의 목적은 다른 메서드에서 사용할 수 있도록 캡슐화 하는것, 
람다로 표현한 구현식의 세부공개를 할 필요 없다. 
```java
    public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
        return points.stream()
                    .map(p -> new Point(p.getX() + x, p.getY()))
                    .collect(toList());
    }

    @Test
    public void testMoveAllPointsRightBy() throws Exception {
        List<Point> points =  Arrays.asList(new Point(5, 5), new Point(10, 5));
        List<Point> expectedPoints =   Arrays.asList(new Point(15, 5), new Point(20, 5));
        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
        assertEquals(expectedPoints, newPoints);
    }
```

### 복잡한 람다를 메소드로 분할 / 고차함수 

복잡한 람다 표현식을 일반 메소드로 선언하여 메소드 참조 방식으로 처리
함수를 파라미터로 받는것을 고차함수라한다. 


## 9.4 디버깅

문제의 코드 발견시 확인할 수 있는 방법
- 스택트레이스
- 로깅

### stack trace

람다는 이름이 없어서 확인이 어려울 수 있다. 
굳이 이름을 찾는다면 'lambda$main$0'
메서드 참조를 사용하면 메소드명으로 노출될 수 있다. 
```java
    public class Debugging{
        public static void main(String[] args) {
            List<Point> points = Arrays.asList(new Point(12, 2), null);
            points.stream().map(p -> p.getX()).forEach(System.out::println);
        }
    }

    Exception in thread "main" java.lang.NullPointerException
    at Debugging.lambda$main$0(Debugging.java:6)
    at Debugging$$Lambda$5/284720968.apply(Unknown Source)
    at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)
    at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers.stream().map(Debugging::divideByZero).forEach(System
         .out::println);
    }
    public static int divideByZero(int n){
        return n / 0;
    }

    Exception in thread "main" java.lang.ArithmeticException: / by zero
    at Debugging.divideByZero(Debugging.java:10)
    at Debugging$$Lambda$1/999966131.apply(Unknown Source)
    at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline

```

### 로깅

파이프라인의 중간연산 결과가 확인이 필요할 경우 peek을 사용하면 된다. 
```java
    IntStream.rangeClosed(1, 10)
            .map(num -> num * 2)
            .peek(num -> System.out.println("#1   " + num))
            .map(num -> num + 1)
            .peek(num -> System.out.println("#2   " + num))
            .boxed().collect(Collectors.toList()); 
```


