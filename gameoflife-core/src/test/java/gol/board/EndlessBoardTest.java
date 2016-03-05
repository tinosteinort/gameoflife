package gol.board;

import gol.Cell;
import org.junit.Test;

import java.util.List;

/**
 * Created by Tino on 21.01.2016.
 */
public class EndlessBoardTest extends CellTest {

    @Test
    public void testGetNeighbours() {

        EndlessBoard board = new EndlessBoard();

        Cell cell = new Cell(0, 0);
        Cell cellWest = new Cell(-1, 0);
        Cell cellNorthWest = new Cell(-1, -1);
        Cell cellNorth = new Cell(0, -1);

        // Do not add dead Cells to Board
        Cell cellNorthEast = new Cell(1, -1);
        Cell cellEast = new Cell(1, 0);
        Cell cellSouthEast = new Cell(1, 1);
        Cell cellSouth = new Cell(0, 1);
        Cell cellSouthWest = new Cell(-1, 1);

        board.add(cell).add(cellWest).add(cellNorthWest).add(cellNorth);

        List<Cell> neighbours = board.getNeighbours(cell);
        assertSameCells(neighbours,
                cellWest, cellNorthWest, cellNorth, cellNorthEast, cellEast, cellSouthEast, cellSouth, cellSouthWest);
    }
}
