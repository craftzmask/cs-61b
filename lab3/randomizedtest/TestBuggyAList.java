package randomizedtest;

/**
 * Created by hug.
 */

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();

        for (int i = 0; i < 3; i++) {
            correct.addLast(i);
            buggy.addLast(i);
        }

        for (int i = 0; i < 3; i++) {
            assertEquals(correct.removeLast(), buggy.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } else if (operationNumber == 1) {
                assertEquals(L.size(), B.size());
            } else if (operationNumber == 2) {
                if (B.size() > 0 && L.size() > 0) {
                    assertEquals(L.getLast(), B.getLast());
                }
            } else if (operationNumber == 3) {
                if (B.size() > 0 && L.size() > 0) {
                    assertEquals(L.removeLast(), B.removeLast());
                }
            }
        }
    }
}
