package task1;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ImmutableQueueTest {

    private static final int N = 10;
    private static final int TEST_COUNT = 1;

    @Test
    public void enQueueTest() {
        System.out.println("enQueueTest, N=" + String.valueOf(N));
        for (int i = 0; i < TEST_COUNT; i++) {
            test1();
        }
        System.out.println("enQueueTest Done.");
    }

    private void test1() {
        Queue<Integer> q0 = new ImmutableQueue<>();
        Queue<Integer> q1 = q0.enQueue(0);
        Queue<Integer> qN = q1;
        Stopwatch s = Stopwatch.createStarted();
        for (int i = 1; i < N; i++) {
            qN = qN.enQueue(i);
        }
        System.out.println(s.stop().elapsed(TimeUnit.NANOSECONDS));

        System.out.println("q0=" + q0.toString());
        System.out.println("q1=" + q1.toString());
        System.out.println("qN=" + qN.toString());
        assertEquals(0, q0.size());
        assertEquals(1, q1.size());
        assertEquals(N, qN.size());
    }

    @Test
    public void deQueueTest() {
        System.out.println("deQueueTest N=" + String.valueOf(N));
        for (int i = 0; i < TEST_COUNT; i++) {
            test2();
        }
        System.out.println("deQueueTest Done.");
    }

    private void test2() {
        Queue<Integer> qN = new ImmutableQueue<>(IntStream.range(0, N).boxed().collect(Collectors.toList()));
        Queue<Integer> qNminus1 = qN.deQueue();
        Queue<Integer> q0 = qNminus1;
        Stopwatch s = Stopwatch.createStarted();
        for (int i = 1; i < N; i++) {
            q0 = q0.deQueue();
        }
        System.out.println(s.stop().elapsed(TimeUnit.NANOSECONDS));

        System.out.println("qN=" + qN.toString());
        System.out.println("q-1=" + qNminus1.toString());
        System.out.println("q0=" + q0.toString());
        assertEquals(N, qN.size());
        assertEquals(N - 1, qNminus1.size());
        assertEquals(0, q0.size());
    }

}
