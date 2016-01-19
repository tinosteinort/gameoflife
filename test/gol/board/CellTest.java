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

    public void assertContainsAll(final Collection<Cell> collectionToTest, final Cell... expectedCells) {
        Objects.requireNonNull(collectionToTest, "Collection required");
        Objects.requireNonNull(expectedCells, "Cells required for assertion");

        for (Cell expectedCell : expectedCells) {

            if (!collectionToTest.contains(expectedCell)) {
                Assert.fail("Cell " + expectedCell + "not found in " + Arrays.toString(expectedCells));
            }
        }
    }
}
