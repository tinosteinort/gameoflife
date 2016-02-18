package gol.rule;

import gol.Status;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Tino on 23.01.2016.
 */
public class StatusCalculatorTest {

    @Test
    public void testConditionsForLivingCells() {

        StatusCalculator calculator = new StatusCalculator();

        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.ALIVE, 0));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.ALIVE, 1));
        Assert.assertEquals(Status.ALIVE, calculator.calculateStatus(Status.ALIVE, 2));
        Assert.assertEquals(Status.ALIVE, calculator.calculateStatus(Status.ALIVE, 3));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.ALIVE, 4));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.ALIVE, 5));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.ALIVE, 6));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.ALIVE, 7));
    }

    @Test
    public void testConditionsForDeadCells() {

        StatusCalculator calculator = new StatusCalculator();

        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.DEAD, 0));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.DEAD, 1));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.DEAD, 2));
        Assert.assertEquals(Status.ALIVE, calculator.calculateStatus(Status.DEAD, 3));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.DEAD, 4));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.DEAD, 5));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.DEAD, 6));
        Assert.assertEquals(Status.DEAD, calculator.calculateStatus(Status.DEAD, 7));
    }
}
