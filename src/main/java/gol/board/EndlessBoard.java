package gol.board;

import gol.Cell;
import gol.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class EndlessBoard extends Board {

    public EndlessBoard() {

    }

    public EndlessBoard(final long initialGeneration) {
        super(initialGeneration);
    }

    @Override
    protected List<Cell> getLivingNeighbours(final Cell cell) {
        return getNeighbours(cell, Status.ALIVE);
    }

    @Override
    protected List<Cell> getDeadNeighbours(final Cell cell) {
        return getNeighbours(cell, Status.DEAD);
    }

    private List<Cell> getNeighbours(final Cell cell, final Status status) {
        final List<Cell> neighbours = new ArrayList<>();

        for (int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
            for (int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
                if (x == cell.getX() && y == cell.getY()) {
                    continue;
                }

                final Cell currentCell = new Cell(x, y);

                final boolean cellIsAlive = cellIsAlive(currentCell);
                if (cellIsAlive && status == Status.ALIVE) {
                    neighbours.add(currentCell);
                }
                else if (!cellIsAlive && status == Status.DEAD) {
                    neighbours.add(currentCell);
                }
            }
        }

        return neighbours;
    }
}
