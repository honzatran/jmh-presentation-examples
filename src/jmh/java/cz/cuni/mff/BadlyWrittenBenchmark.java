package cz.cuni.mff;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Created by honza on 08/05/2017.
 */

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
public class BadlyWrittenBenchmark {

    @Benchmark
    public void sumBadBenchmark() {
        int a = 1;
        int b = 2;
        int sum = a + b;
    }

    @Benchmark
    public int sumReturnBenchmark() {
        int a = 1;
        int b = 2;
        int sum = a + b;
        return sum;
    }

    @Benchmark
    public void sumBlackholeBenchmark(Blackhole blackhole) {
        int a = 1;
        int b = 2;
        int sum = a + b;
        blackhole.consume(sum);
    }
}
