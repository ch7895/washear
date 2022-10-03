## Chapter9

## 9.1 Refactoring for improved readability and flexibillity

### improving code readabillity

- 좋은가독성이란? 다른사람의 코드가 얼마나 쉽게 이해되는가
- 익명클래스 -> 람다
- 람다 -> 메소드 참조
- 스트림

### 익명클래스
```java
    Runnable r1 = new Runnable() {
        public void run(){
            System.out.println("Hello");
        }
    };

    Runnable r2 = () -> System.out.println("Hello");

```