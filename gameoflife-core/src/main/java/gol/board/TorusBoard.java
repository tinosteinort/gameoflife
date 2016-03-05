package gol.board;

import gol.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class TorusBoard extends BoundedBoard {

    public TorusBoard(final int width, final int height) {
        super(width, height);
    }

    public TorusBoard(final long initialGeneration, final int width, final int height) {
        super(initialGeneration, width, height);
    }

    @Override
    protected List<Cell> getNeighbours(final gol.Cell cell) {
        final List<Cell> neighbours = new ArrayList<>(NEIGHBOUR_COUNT);

        for (int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
            for (int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
                if (x == cell.getX() && y == cell.getY()) {
                    continue;
                }

                neighbours.add(new Cell(translateX(x), translateY(y)));
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
