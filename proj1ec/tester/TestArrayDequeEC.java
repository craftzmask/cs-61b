package tester;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

import static org.junit.Assert.assertEquals;

public class TestArrayDequeEC {
    @Test
    public void randomTest() {
        StudentArrayDeque<Integer> buggy = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> correct = new ArrayDequeSolution<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 100; i += 1) {
            int random = StdRandom.uniform(0, 4);

            if (random == 0) {
                buggy.addLast(i);
                correct.addLast(i);
                sb.append("addLast(" + i + ")\n");
            } else if (random == 1) {
                buggy.addFirst(i);
                correct.addFirst(i);
                sb.append("addFirst(" + i + ")\n");
            } else if (random == 2) {
                sb.append("removeFirst()\n");
                if (!buggy.isEmpty() && !correct.isEmpty()) {
                    assertEquals(sb.toString(), correct.removeFirst(), buggy.removeFirst());
                }
            } else if (random == 3) {
                sb.append("removeLast()\n");
                if (!buggy.isEmpty() && !correct.isEmpty()) {
                    assertEquals(sb.toString(), correct.removeLast(), buggy.removeLast());
                }
            }
        }
    }
}
