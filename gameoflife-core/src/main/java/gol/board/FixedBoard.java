package gol.board;

import gol.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class FixedBoard extends BoundedBoard {

    public FixedBoard(final int width, final int height) {
        super(width, height);
    }

    public FixedBoard(final long initialGeneration, final int width, final int height) {
        super(initialGeneration, width, height);
    }

    @Override
    protected List<Cell> getNeighbours(final Cell cell) {
        final List<Cell> neighbours = new ArrayList<>(NEIGHBOUR_COUNT);

        for (int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
            for (int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
                if (!pointIsInBound(x, y)
                        || (x == cell.getX() && y == cell.getY())) {
                    continue;
                }

                neighbours.add(new Cell(x, y));
            }
        }

        return neighbours;
    }

    boolean pointIsInBound(final int x, final int y) {
        return x >= 0 && x < getWidth()
                && y >= 0 && y < getHeight();
    }
}
