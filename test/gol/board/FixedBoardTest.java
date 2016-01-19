package gol.board;

import gol.Cell;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class FixedBoardTest extends CellTest {

    @Test
    public void testWithinBounds() {

        FixedBoard board = new FixedBoard(10, 5);

        Assert.assertTrue(board.pointIsInBound(0, 0));
        Assert.assertTrue(board.pointIsInBound(9, 0));
        Assert.assertTrue(board.pointIsInBound(0, 4));
        Assert.assertTrue(board.pointIsInBound(9, 4));
    }

    @Test
    public void testNotInBounds() {

        FixedBoard board = new FixedBoard(10, 5);

        Assert.assertFalse(board.pointIsInBound(-1, -1));
        Assert.assertFalse(board.pointIsInBound(10, -1));
        Assert.assertFalse(board.pointIsInBound(-1, 5));
        Assert.assertFalse(board.pointIsInBound(10, 5));
    }

    @Test
    public void testGetLivingNeighbours() {

        FixedBoard board = new FixedBoard(10, 5);

        Cell cell = new Cell(3, 3);
        Cell cellWest = new Cell(2, 3);
        Cell cellNorthWest = new Cell(2, 2);
        Cell cellNorth = new Cell(3, 2);

        board.add(cell).add(cellWest).add(cellNorthWest).add(cellNorth);

        List<Cell> neighbours = board.getLivingNeighbours(cell);
        assertContainsAll(neighbours, cellWest, cellNorthWest, cellNorth);
    }

    @Test
    public void testGetDeadNeighbours() {

        FixedBoard board = new FixedBoard(10, 5);

        Cell cell = new Cell(3, 3);
        Cell cellWest = new Cell(2, 3);
        Cell cellNorthWest = new Cell(2, 2);
        Cell cellNorth = new Cell(3, 2);

        board.add(cell).add(cellWest).add(cellNorthWest).add(cellNorth);

        // Do not add dead Cells to Board
        Cell cellNorthEast = new Cell(4, 2);
        Cell cellEast = new Cell(4, 3);
        Cell cellSouthEast = new Cell(4, 4);
        Cell cellSouth = new Cell(3, 4);
        Cell cellSouthWest = new Cell(2, 4);


        List<Cell> neighbours = board.getDeadNeighbours(cell);
        assertContainsAll(neighbours, cellNorthEast, cellEast, cellSouthEast, cellSouth, cellSouthWest);
    }
}
