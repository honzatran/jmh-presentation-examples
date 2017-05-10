package cz.cuni.mff;

import com.carrotsearch.hppc.IntArrayList;
import com.carrotsearch.hppc.IntIntHashMap;
import com.carrotsearch.hppc.IntObjectHashMap;
import com.carrotsearch.hppc.IntObjectMap;
import org.apache.commons.math3.analysis.function.Min;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by honza on 09/05/2017.
 */

@State(Scope.Benchmark) @OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 5)
@Fork(1)
@Warmup(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
public class HashMapBenchmark {
    private static final Random RANDOM = new Random();

    public static final int MIN = 0;
    public static final int MAX = 1_000_000_000;

    @Param({"100", "10000", "1000000"})
    public static int size;


    @State(Scope.Thread)
    public static class MapState {
        public HashMap<Integer, Integer> map;

        public List<Integer> findValues;
        public int next;

        @Setup(Level.Trial)
        public void setUp() {
            map = new HashMap<>();
            findValues = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                map.put(RANDOM.nextInt(size), RANDOM.nextInt(MAX));
                findValues.add(RANDOM.nextInt(size));
            }
        }

        @Setup(Level.Iteration)
        public void setValueSearch() {
            next = 0;
        }

        public int getNextValue() {
            Integer value = findValues.get(next++);

            if (next >= size) {
                next = 0;
            }

            return value;
        }
    }

    @State(Scope.Thread)
    public static class HppcState {
        public IntIntHashMap map;
        public int valueSearch;
        public IntArrayList findValues;
        private int next = 0;

        @Setup(Level.Trial)
        public void fillMap() {
            map = new IntIntHashMap();
            findValues = new IntArrayList(size);

            for (int i = 0; i < size; i++) {
                map.put(RANDOM.nextInt(size), RANDOM.nextInt(MAX));

                findValues.add(RANDOM.nextInt(size));
            }
        }

        @Setup(Level.Iteration)
        public void setValueSearch() {
            next = 0;
        }

        int getNextValue() {
            int value = findValues.get(next++);
            if (next >= size) {
                next = 0;
            }

            return value;
        }
    }

    @Benchmark
    public Integer mapBenchmark(MapState state) {
        return state.map.get(state.getNextValue());
    }

    @Benchmark
    public int linearProbingOpenAdressingBenchmark(HppcState state) {
        return state.map.get(state.getNextValue());
    }
}
