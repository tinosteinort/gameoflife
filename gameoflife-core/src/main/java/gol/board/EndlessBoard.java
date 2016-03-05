package gol.board;

import gol.Cell;

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
    protected List<Cell> getNeighbours(final Cell cell) {
        final List<Cell> neighbours = new ArrayList<>(NEIGHBOUR_COUNT);

        for (int x = cell.getX() - 1; x <= cell.getX() + 1; x++) {
            for (int y = cell.getY() - 1; y <= cell.getY() + 1; y++) {
                if (x == cell.getX() && y == cell.getY()) {
                    continue;
                }

                neighbours.add(new Cell(x, y));
            }
        }

        return neighbours;
    }
}
