package com.example.washere;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;



@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkChap7 {

    final int N = 10000;
    final List<Integer> array = new ArrayList<>();
    
	@Setup
	public void init() {
        // 성능 측정 전 사전에 필요한 작업
        for(int i = 0; i < N; i++) {
            array.add(i);
        }
	}

	@Benchmark
    public long sequentialSum() {
    return Stream.iterate(1L, i -> i + 1).limit(N)
                    .reduce( 0L, Long::sum);
    }

    @Benchmark
    public long iterativeSum() {
        long result = 0;
        for (long i = 1L; i <= N; i++) {
            result += i; }
        return result;
    }

    @Benchmark
    public long rangedSum() {
        return LongStream.rangeClosed(1, N)
                     .reduce(0L, Long::sum);
    }

    Integer temp = 0;
    public void processor(Integer i) {
        temp = i;
    }

    public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkChap7.class.getSimpleName())
                .warmupIterations(10)           // 사전 테스트 횟수
                .measurementIterations(10)      // 실제 측정 횟수
                .forks(1)                       // 
                .build();
        new Runner(opt).run();                  // 벤치마킹 시작
    }
}

