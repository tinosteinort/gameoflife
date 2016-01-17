package gol.rule;

import gol.Status;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tino on 17.01.2016.
 */
public class RecreationRuleTest {

    @Test
    public void testCreateNewCell() {
        Rule rule = new RecreationRule();

        Assert.assertTrue(rule.matches(Status.DEAD, 3));
    }
    @Test
    public void testNoRecreateCell() {
        Rule rule = new RecreationRule();

        Assert.assertFalse(rule.matches(Status.DEAD, 0));
        Assert.assertFalse(rule.matches(Status.DEAD, 1));
        Assert.assertFalse(rule.matches(Status.DEAD, 2));
        Assert.assertFalse(rule.matches(Status.DEAD, 4));

        Assert.assertFalse(rule.matches(Status.ALIVE, 3));
    }
}
