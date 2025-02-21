package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    @Test
    public void maxTest() {
        Comparator<Integer> c = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };

        MaxArrayDeque<Integer> max = new MaxArrayDeque<>(c);
        max.addLast(20);
        max.addLast(10);
        max.addLast(5);
        max.addLast(100);

        assertEquals(100, (int) max.max());
    }

    @Test
    public void emptyTest() {
        Comparator<Integer> c = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };

        MaxArrayDeque<Integer> max = new MaxArrayDeque<>(c);
        assertNull(max.max());
    }

    @Test
    public void testEquals() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
            lld2.addLast(i);
        }

        assertTrue(lld1.equals(lld2));
    }

    @Test
    public void testEqualsDeque() {
        Deque<Integer> lld1 = new ArrayDeque<>();
        Deque<Integer> lld2 = new LinkedListDeque<>();
        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
            lld2.addLast(i);
        }

        assertTrue(lld1.equals(lld2));
    }
}
