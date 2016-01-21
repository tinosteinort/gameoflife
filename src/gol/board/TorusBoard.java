package gol.board;

import gol.Cell;
import gol.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class TorusBoard extends BoundedBoard {

    public TorusBoard(final int width, final int height) {
        super(width, height);
    }

    @Override
    protected List<Cell> getLivingNeighbours(final Cell cell) {
        return getNeighbours(cell, Status.ALIVE);
    }

    @Override
    protected List<Cell> getDeadNeighbours(final Cell cell) {
        return getNeighbours(cell, Status.DEAD);
    }

    private List<Cell> getNeighbours(final gol.Cell cell, final Status status) {
        final List<Cell> neighbours = new ArrayList<>();

        for (int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
            for (int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
                if (x == cell.getX() && y == cell.getY()) {
                    continue;
                }

                final Cell currentCell = new Cell(translateX(x), translateY(y));

                final boolean cellIsAlive = contains(currentCell);
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

    int translateX(final int x) {
        if (x < 0) {
            return getWidth() - 1;
        }
        if (x >= getWidth()) {
            return 0;
        }
        return x;
    }

    int translateY(final int y) {
        if (y < 0) {
            return getHeight() - 1;
        }
        if (y >= getHeight()) {
            return 0;
        }
        return y;
    }
}
