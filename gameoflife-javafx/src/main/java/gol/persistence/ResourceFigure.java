package gol.persistence;

import gol.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tino on 06.03.2016.
 */
public class ResourceFigure {

    private final String name;
    private final List<Cell> cells = new ArrayList<>();

    public ResourceFigure(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
