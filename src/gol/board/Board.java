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

    private final static long START_GENERATION = 1;
    private long currentGeneration = START_GENERATION;

    public void nextRound() {

        calculateNextStatusOfCells(Status.ALIVE, livingCells);
        calculateNextStatusOfCells(Status.DEAD, determineDeadNeigbourCells());

        updateLivingCells();

        currentGeneration++;
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

    private void updateLivingCells() {
        livingCells.clear();
        livingCells.addAll(newLivingCells);
        newLivingCells.clear();
    }

    protected boolean cellIsAlive(final Cell cell) {
        return livingCells.contains(cell);
    }

    public Board add(final Cell cell) {
        livingCells.add(cell);
        return this;
    }

    public Board addAll(final Collection<Cell> cells) {
        livingCells.addAll(cells);
        return this;
    }

    public Board clear() {
        livingCells.clear();
        currentGeneration = START_GENERATION;
        return this;
    }

    protected abstract List<Cell> getLivingNeighbours(Cell cell);

    protected abstract List<Cell> getDeadNeighbours(Cell cell);

    public List<Cell> getLivingCells() {
        return Collections.unmodifiableList(livingCells);
    }

    public long getCurrentGeneration() {
        return currentGeneration;
    }
}
