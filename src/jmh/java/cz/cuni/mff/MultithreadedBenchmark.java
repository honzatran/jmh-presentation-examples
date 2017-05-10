package cz.cuni.mff;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by honza on 10/05/2017.
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
public class MultithreadedBenchmark {

    @Param ({"0", "100", "10000"})
    public int backoff;

    @State(Scope.Group)
    public static class BlockingQueueState {
        BlockingQueue<Long> queue;
        long value = 42;

        @Setup(Level.Trial)
        public void setUp() {
            queue = new ArrayBlockingQueue<>(1024);
        }
    }

    @State(Scope.Group)
    public static class ConcurrentQueueState {
        ConcurrentLinkedQueue<Long> queue;
        long value = 42;

        @Setup(Level.Trial)
        public void setUp() {
            queue = new ConcurrentLinkedQueue<>();
        }

    }

    @Group("Blocking")
    @GroupThreads(1)
    @Benchmark
    public Long takeBlockingBenchmark(Blackhole blackhole, BlockingQueueState state) throws InterruptedException {
        // simulate some work
        Blackhole.consumeCPU(backoff);
        return state.queue.take();
    }

    @Group("Blocking")
    @Benchmark
    public void offerBlockingBenchmark(BlockingQueueState state) throws InterruptedException {
        state.queue.offer(state.value);
        Blackhole.consumeCPU(backoff);
        // simulate some work
    }

    @Group("Concurrent")
    @GroupThreads(1)
    @Benchmark
    public Long takeConcurrentBenchmark(ConcurrentQueueState state) throws InterruptedException {
        Blackhole.consumeCPU(backoff);
        return state.queue.poll();
    }

    @Group("Concurrent")
    @Benchmark
    public void offerConcurrentBenchmark(ConcurrentQueueState state) throws InterruptedException {
        state.queue.offer(state.value);
        Blackhole.consumeCPU(backoff);
    }

}
