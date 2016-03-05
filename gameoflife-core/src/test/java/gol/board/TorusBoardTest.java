package gol.board;

import gol.Cell;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tino on 19.01.2016.
 */
public class TorusBoardTest extends CellTest {

    @Test
    public void testTranslateX_default() {

        TorusBoard board = new TorusBoard(20, 10);
        Assert.assertEquals(5, board.translateX(5));
    }

    @Test
    public void testTranslateX_leftToRight() {

        TorusBoard board = new TorusBoard(20, 10);
        Assert.assertEquals(19, board.translateX(-1));
    }

    @Test
    public void testTranslateX_rightToLeft() {

        TorusBoard board = new TorusBoard(20, 10);
        Assert.assertEquals(0, board.translateX(20));
    }

    @Test
    public void testTranslateY_default() {

        TorusBoard board = new TorusBoard(20, 10);
        Assert.assertEquals(7, board.translateY(7));
    }

    @Test
    public void testTranslateY_topToBottom() {

        TorusBoard board = new TorusBoard(20, 10);
        Assert.assertEquals(9, board.translateY(-1));
    }

    @Test
    public void testTranslateY_bottomToTop() {

        TorusBoard board = new TorusBoard(20, 10);
        Assert.assertEquals(0, board.translateY(10));
    }

    @Test
    public void testGetNeighbours_top_left() {

        TorusBoard board = new TorusBoard(20, 10);

        Cell cell = new Cell(0, 0);
        Cell cellWest = new Cell(19, 0);
        Cell cellNorthWest = new Cell(19, 9);
        Cell cellNorth = new Cell(0, 9);

        // Do not add dead Cells to Board
        Cell cellNorthEast = new Cell(1, 9);
        Cell cellEast = new Cell(1, 0);
        Cell cellSouthEast = new Cell(1, 1);
        Cell cellSouth = new Cell(0, 1);
        Cell cellSouthWest = new Cell(19, 1);

        board.add(cell).add(cellWest).add(cellNorthWest).add(cellNorth);

        List<Cell> neighbours = board.getNeighbours(cell);
        assertSameCells(neighbours,
                cellWest, cellNorthWest, cellNorth, cellNorthEast, cellEast, cellSouthEast, cellSouth, cellSouthWest);
    }

    @Test
    public void testGetNeighbours_bottom_left() {

        TorusBoard board = new TorusBoard(20, 10);

        Cell cell = new Cell(0, 9);
        Cell cellWest = new Cell(19, 9);
        Cell cellSouthWest = new Cell(19, 0);
        Cell cellSouth = new Cell(0, 0);

        // Do not add dead Cells to Board
        Cell cellNorthEast = new Cell(19, 8);
        Cell cellNorth = new Cell(0, 8);
        Cell cellNorthWest = new Cell(1, 8);
        Cell cellEast = new Cell(1, 9);
        Cell cellSouthEast = new Cell(1, 0);

        board.add(cell).add(cellWest).add(cellSouthWest).add(cellSouth);

        List<Cell> neighbours = board.getNeighbours(cell);
        assertSameCells(neighbours,
                cellWest, cellSouthWest, cellSouth, cellNorthEast, cellNorth, cellNorthWest, cellEast, cellSouthEast);
    }

    @Test
    public void testGetNeighbours_top_right() {

        TorusBoard board = new TorusBoard(20, 10);

        Cell cell = new Cell(19, 0);
        Cell cellEast = new Cell(0, 0);
        Cell cellNorthEast = new Cell(0, 9);
        Cell cellNorth = new Cell(19, 9);

        // Do not add dead Cells to Board
        Cell cellNorthWest = new Cell(18, 9);
        Cell cellWest = new Cell(18, 0);
        Cell cellSouthWest = new Cell(18, 1);
        Cell cellSouth = new Cell(19, 1);
        Cell cellSouthEast = new Cell(0, 1);

        board.add(cell).add(cellEast).add(cellNorthEast).add(cellNorth);

        List<Cell> neighbours = board.getNeighbours(cell);
        assertSameCells(neighbours,
                cellEast, cellNorthEast, cellNorth, cellNorthWest, cellWest, cellSouthWest, cellSouth, cellSouthEast);
    }

    @Test
    public void testGetNeighbours_bottom_right() {

        TorusBoard board = new TorusBoard(20, 10);

        Cell cell = new Cell(19, 9);
        Cell cellEast = new Cell(0, 9);
        Cell cellSouthEast = new Cell(0, 0);
        Cell cellSouth = new Cell(19, 0);

        // Do not add dead Cells to Board
        Cell cellSouthWest = new Cell(18, 0);
        Cell cellWest = new Cell(18, 9);
        Cell cellNorthWest = new Cell(18, 8);
        Cell cellNorth = new Cell(19, 8);
        Cell cellNorthEast = new Cell(0, 8);

        board.add(cell).add(cellEast).add(cellSouthEast).add(cellSouth);

        List<Cell> neighbours = board.getNeighbours(cell);
        assertSameCells(neighbours,
                cellEast, cellSouthEast, cellSouth, cellSouthWest, cellWest, cellNorthWest, cellNorth, cellNorthEast);
    }

    @Test
    public void testGetNeighbours_default() {

        TorusBoard board = new TorusBoard(20, 10);

        Cell cell = new Cell(5, 5);
        Cell cellEast = new Cell(6, 5);
        Cell cellSouthEast = new Cell(6, 6);
        Cell cellSouth = new Cell(5, 6);

        // Do not add dead Cells to Board
        Cell cellSouthWest = new Cell(4, 6);
        Cell cellWest = new Cell(4, 5);
        Cell cellNorthWest = new Cell(4, 4);
        Cell cellNorth = new Cell(5, 4);
        Cell cellNorthEast = new Cell(6, 4);

        board.add(cell).add(cellEast).add(cellSouthEast).add(cellSouth);

        List<Cell> neighbours = board.getNeighbours(cell);
        assertSameCells(neighbours,
                cellEast, cellSouthEast, cellSouth, cellSouthWest, cellWest, cellNorthWest, cellNorth, cellNorthEast);
    }
}
