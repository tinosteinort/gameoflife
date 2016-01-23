package gol.rule;

import gol.Status;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tino on 17.01.2016.
 */
public class OverPopulationRuleTest {

    @Test
    public void testOverPopulationOfLivingCell() {
        Rule rule = new OverPopulationRule();

        Assert.assertFalse(rule.matches(Status.ALIVE, 2));
        Assert.assertFalse(rule.matches(Status.ALIVE, 3));
        Assert.assertTrue(rule.matches(Status.ALIVE, 4));
        Assert.assertTrue(rule.matches(Status.ALIVE, 5));
    }

    @Test
    public void testOverPopulationOfDeadCell() {
        Rule rule = new OverPopulationRule();

        Assert.assertFalse(rule.matches(Status.DEAD, 2));
        Assert.assertFalse(rule.matches(Status.DEAD, 3));
        Assert.assertTrue(rule.matches(Status.DEAD, 4));
        Assert.assertTrue(rule.matches(Status.DEAD, 5));
    }
}
