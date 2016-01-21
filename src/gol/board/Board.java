package gol.board;

import gol.Cell;
import gol.Status;
import gol.rule.StatusCalculator;

import java.util.*;

/**
 * Created by Tino on 17.01.2016.
 */
public abstract class Board {

    private final StatusCalculator calculator = new StatusCalculator();

    private final List<Cell> livingCells = new ArrayList<>();
    private final List<Cell> newLivingCells = new ArrayList<>();

    public void nextRound() {

        calculateNextStatusOfCells(Status.ALIVE, livingCells);
        calculateNextStatusOfCells(Status.DEAD, determineDeadNeigbourCells());
    }

    private void calculateNextStatusOfCells(final Status currentStatus, final Collection<Cell> cells) {
        for (Cell cell : cells) {

            final List<Cell> neighbours = getLivingNeighbours(cell);
            final Status newStatus = calculator.calculateStatus(currentStatus, neighbours.size());

            switch (newStatus) {
                case ALIVE:
                    newLivingCells.add(cell);
                    break;
            }
        }
    }

    private Set<Cell> determineDeadNeigbourCells() {
        final Set<Cell> deadNeighbours = new HashSet<>();
        for (Cell cell : livingCells) {
            deadNeighbours.addAll(getDeadNeighbours(cell));
        }
        return deadNeighbours;
    }

    protected boolean cellIsAlive(final Cell cell) {
        return livingCells.contains(cell);
    }

    public Board add(final Cell cell) {
        livingCells.add(cell);
        return this;
    }

    protected abstract List<Cell> getLivingNeighbours(Cell cell);

    protected abstract List<Cell> getDeadNeighbours(Cell cell);

    public List<Cell> getLivingCells() {
        return Collections.unmodifiableList(livingCells);
    }
}
