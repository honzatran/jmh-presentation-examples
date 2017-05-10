package cz.cuni.mff;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by honza on 09/05/2017.
 */

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 5)
@Fork(1)
@Warmup(iterations = 5)
@BenchmarkMode(Mode.SampleTime)
public class ArrayCopyBenchmark {
    final Random random = new Random();

    @Param({"1000000"})
    public int size;

    byte[] src;
    byte[] dst;


    @Setup(Level.Trial)
    public void setUp() {
        src = new byte[size];
        dst = new byte[size];
    }

    @Setup(Level.Iteration)
    public void fillSrc() {
        random.nextBytes(src);
    }

    @Benchmark
    public void manualCopy() {
        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i];
        }
    }

    @Benchmark
    public void systemArrayCopy() {
        System.arraycopy(src, 0, dst, 0, src.length);
    }
}
