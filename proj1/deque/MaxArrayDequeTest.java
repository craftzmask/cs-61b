package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
}
