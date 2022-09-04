## Chapter7

## 7.1 parallel streams

- parallelstream 은 여러개의 스레드에서 일을 나누어 처리할 수 있도록 하는 스트림이다. 
 

#### 병렬로 전환
```java
public long parallelSum(long n) {
    return Stream.iterate(1L, i -> i + 1)
                 .limit(n)
                 .parallel()
                 .reduce(0L, Long::sum);
}
```

- parallel 함수 호출시 내부적으로 boolean 플래그를 설정한다. 이후연산은 병렬적으로 수행된다.
- 반대로 다시 순차스트림으로 다시 변환할 수도 있다. 
- pallere, sequential 중 마지막으로 호출된 메서드는 전체 파이프라인에 영향을 미친다. 

#### 스트림 성능측정 (Jmh)

- java microbenchmark harness
- 어노테이션 기반

*Benchmark* 결과
|Benchmark|                              Mode|  Cnt|    Score|    Error|  Units|
|-|-|-|-|-|-|
|ParallelStreamBenchmark.sequentialSum|  avgt|   40|  121.843±|  3.062|  ms/op|
|ParallelStreamBenchmark.iterativeSum|   avgt|   40|  3.278±|    0.192|  ms/op|
|ParallelStreamBenchmark.parallelSum|    avgt|   40|  604.059±| 55.288|  ms/op|

- iterate는 박스된 객체를 생성한다. 더하기전에 언박싱 해야한다.  
- iterate는 parallel 에서는 독립된 청크로 나누기 어렵다 

두번째 이슈가 특히 흥미롭다. 왜냐면 mental model을 유지할 필요가 있다. 병렬 처리를 위해. 
