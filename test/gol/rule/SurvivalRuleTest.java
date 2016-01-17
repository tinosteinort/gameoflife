package gol.rule;

import gol.Status;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tino on 17.01.2016.
 */
public class SurvivalRuleTest {

    @Test
    public void testSurvivalOfCell() {
        Rule rule = new SurvivalRule();

        Assert.assertTrue(rule.matches(Status.ALIVE, 2));
        Assert.assertTrue(rule.matches(Status.ALIVE, 3));
    }
    @Test
    public void testNoMatch() {
        Rule rule = new SurvivalRule();

        Assert.assertFalse(rule.matches(Status.ALIVE, 0));
        Assert.assertFalse(rule.matches(Status.ALIVE, 1));
        Assert.assertFalse(rule.matches(Status.ALIVE, 4));

        Assert.assertFalse(rule.matches(Status.DEAD, 2));
        Assert.assertFalse(rule.matches(Status.DEAD, 3));
    }
}
