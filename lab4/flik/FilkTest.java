package flik;

import org.junit.Assert;
import org.junit.Test;

public class FilkTest {
    @Test
    public void testSame() {
        Flik flik = new Flik();
        Assert.assertTrue(flik.isSameNumber(128, 128));
    }

    @Test public void testDifferent() {
        Flik flik = new Flik();
        Assert.assertFalse(flik.isSameNumber(100, 128));
    }
}
