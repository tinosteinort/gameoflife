package gol.rule;

import gol.Status;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tino on 17.01.2016.
 */
public class UnderPopulationRuleTest {

    @Test
    public void testOverPopulationOfCell() {
        Rule rule = new UnderPopulationRule();

        Assert.assertTrue(rule.matches(Status.ALIVE, 0));
        Assert.assertTrue(rule.matches(Status.ALIVE, 1));
    }
    @Test
    public void testNoMatch() {
        Rule rule = new UnderPopulationRule();

        Assert.assertFalse(rule.matches(Status.ALIVE, 2));
        Assert.assertFalse(rule.matches(Status.ALIVE, 3));
        Assert.assertFalse(rule.matches(Status.ALIVE, 4));
        Assert.assertFalse(rule.matches(Status.ALIVE, 5));

        Assert.assertFalse(rule.matches(Status.DEAD, 0));
        Assert.assertFalse(rule.matches(Status.DEAD, 1));
    }
}
