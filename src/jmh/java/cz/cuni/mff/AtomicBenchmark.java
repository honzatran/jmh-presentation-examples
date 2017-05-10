package cz.cuni.mff;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by honza on 09/05/2017.
 */

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class AtomicBenchmark {
    private AtomicLong n;

    @Setup
    public void setup() {
        n = new AtomicLong();
    }

    @Benchmark
    public long test() {
        return n.incrementAndGet();
    }
}
