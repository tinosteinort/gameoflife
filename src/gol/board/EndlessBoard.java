package gol.board;

import gol.Cell;

import java.util.List;

/**
 * Created by Tino on 17.01.2016.
 */
public class EndlessBoard extends Board {

    @Override
    protected List<Cell> getLivingNeighbours(final Cell cell) {
        return null;
    }

    @Override
    protected List<Cell> getDeadNeighbours(final Cell cell) {
        return null;
    }
}
