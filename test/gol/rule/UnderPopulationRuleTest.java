package gol.rule;

import gol.Status;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tino on 17.01.2016.
 */
public class UnderPopulationRuleTest {

    @Test
    public void testUnderPopulationOfLivingCell() {
        Rule rule = new UnderPopulationRule();

        Assert.assertTrue(rule.matches(Status.ALIVE, 0));
        Assert.assertTrue(rule.matches(Status.ALIVE, 1));
        Assert.assertFalse(rule.matches(Status.ALIVE, 2));
        Assert.assertFalse(rule.matches(Status.ALIVE, 3));
    }
    
    @Test
    public void testUnderPopulationOfDeadCell() {
        Rule rule = new UnderPopulationRule();

        Assert.assertTrue(rule.matches(Status.DEAD, 0));
        Assert.assertTrue(rule.matches(Status.DEAD, 1));
        Assert.assertTrue(rule.matches(Status.DEAD, 2));
        Assert.assertTrue(rule.matches(Status.DEAD, 3));
        Assert.assertTrue(rule.matches(Status.DEAD, 4));
        Assert.assertTrue(rule.matches(Status.DEAD, 5));
    }
}
