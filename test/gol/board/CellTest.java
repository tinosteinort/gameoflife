package gol.board;

import gol.Cell;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by Tino on 19.01.2016.
 */
public abstract class CellTest {

    public void assertSameCells(final Collection<Cell> collectionToTest, final Cell... expectedCells) {
        Objects.requireNonNull(collectionToTest, "Collection required");
        Objects.requireNonNull(expectedCells, "Cells required for assertion");

        Assert.assertEquals("Invalid Number of Cells", expectedCells.length, collectionToTest.size());

        for (Cell expectedCell : expectedCells) {

            if (!collectionToTest.contains(expectedCell)) {
                Assert.fail("Cell " + expectedCell + " not found in " + collectionToTest);
            }
        }
    }
}
