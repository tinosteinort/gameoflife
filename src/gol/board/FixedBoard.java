package gol.board;

import gol.Cell;

import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class FixedBoard extends BoundedBoard {

    public FixedBoard(final int width, final int height) {
        super(width, height);
    }

    @Override
    protected List<Cell> getLivingNeighbours(final Cell cell) {
        return null;
    }

    @Override
    protected List<Cell> getDeadNeighbours(final Cell cell) {
        return null;
    }
}
