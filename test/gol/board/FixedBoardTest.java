package gol.board;

import gol.Cell;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class FixedBoardTest {

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

        Cell cell = new Cell(0, 0);
        Cell cellWest = new Cell(-1, 0);
        Cell cellNorthWest = new Cell(-1, -1);
        Cell cellNorth = new Cell(0, -1);

        board.add(cell).add(cellWest).add(cellNorthWest).add(cellNorth);

        List<Cell> neighbours = board.getLivingNeighbours(cell);
        for (Cell neighbour : neighbours) {
            if (cellWest.equals(neighbour)) {
                Assert.assertTrue(true);
            }
            else if (cellNorthWest.equals(neighbour)) {
                Assert.assertTrue(true);
            }
            else if (cellNorth.equals(neighbour)) {
                Assert.assertTrue(true);
            }
            else {
                Assert.assertTrue("Not an expected Cell: " + neighbour, false);
            }
        }
    }

    @Test
    public void testGetDeadNeighbours() {

        FixedBoard board = new FixedBoard(10, 5);

        Cell cell = new Cell(0, 0);
        Cell cellWest = new Cell(-1, 0);
        Cell cellNorthWest = new Cell(-1, -1);
        Cell cellNorth = new Cell(0, -1);

        board.add(cell).add(cellWest).add(cellNorthWest).add(cellNorth);

        // Do not add dead Cells to Board
        Cell cellEast = new Cell(1, 0);
        Cell cellSouthEast = new Cell(1, 1);
        Cell cellSouth = new Cell(0, 1);


        List<Cell> neighbours = board.getLivingNeighbours(cell);
        for (Cell neighbour : neighbours) {
            if (cellEast.equals(neighbour)) {
                Assert.assertTrue(true);
            }
            else if (cellSouthEast.equals(neighbour)) {
                Assert.assertTrue(true);
            }
            else if (cellSouth.equals(neighbour)) {
                Assert.assertTrue(true);
            }
            else {
                Assert.assertTrue("Not an expected Cell: " + neighbour, false);
            }
        }
    }
}
