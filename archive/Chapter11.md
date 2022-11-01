## Chapter11

## Optional Class

- optional은 선택형 값을 캡슐화 하는 클래스이며, 값이 있으면 Optional 클래스는 값을 감싸고 값이 없으면 Optional.empty 메서드로 Optional 객체를 반환

```java
    public class Person {
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
        }
    }

    public class Car {
        private Optional<Insurance> insurance;

        public Optional<Insurance> getInsurance() {
            return insurance;
        }
    }

    public class Insurance {
        private String name;

        public String getName() {
            return name;
        }
    }
```
- 사람은 차를 소유할 수도 있고 아닐수도 있다. 자동차는 포함에 가입했을수도 안했을수도
- 보험은 반드시 이름을 가지고 있어야 한다.

```java
    //java.util.Optional
    private static final java.util.Optional<?> EMPTY;
    private final T value;
```

## Optional 적용패턴 

### Optional 객체생성
- 빈 Optional
```java
    Optioanl<Car> optCar = Optional.empty();
```

- null 이 아닌 값으로 Optional 생성
```java
    Optional<Car> optCar = Optional.of(car);
```

- null 값으로 Optional 생성
```java
    Optional<Car> optCar = optional.ofNullable(car);
```

### Optional 값 추출

```java
    String name = null;
    if (insurance != null) {
        name = insurance.getName();
    }
```
- optional
- 여기서 map은 스트림에서 쓰던 map 함수와 비슷
```java
    Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
    Optional<String> name = optInsurance.map(Insurance::getName);
```

### Optioanl 값 추출 (flaMap 사용)

```java
    Optional<Person> optionalPerson = Optional.of(person);
    String name = optionalPerson.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("UNKNOWN");
```
- 도메인 모델에서 optioanl을 사용할 경우 직렬화 되지 않음 

### Optional 스트림

- java9 에서는 optional에 stream을 추가 
```java
    public Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                .map(Person::getCar) // 사람 목록을 각 사람이 보유한 자동차의 Optional<Car> 스트림으로 변환
                .map(optCar -> optCar.flatMap(Car::getInsurance)) //flatMap 연산을 통해 Optional<Car>를 해당 Optional<Insurane>로 변환
                .map(optInsurance -> optInsurance.map(Insurance::getName)) // Optional<Insuracne>를 해당 이름의 Optional<String>으로 변환
                .flatMap(Optional::stream) // Stream<Optional<String>>을 이름을 포함하는 Stream<String>으로 변환
                .collect(Collectors.toSet());
    }

    // Stream<Optional<String>> 에서 Null 이 포함될 수 있다.
    Set<String> collect = optionalStream.filter(Optional::isPresent)
                                        .map(Optional::get)
                                        .collect(Collectors.toSet());
```

###