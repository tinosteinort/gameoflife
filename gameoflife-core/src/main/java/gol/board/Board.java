package gol.board;

import gol.Cell;
import gol.Status;
import gol.rule.StatusCalculator;

import java.util.*;

/**
 * Created by Tino on 17.01.2016.
 */
public abstract class Board {

    public static final int NEIGHBOUR_COUNT = 8;

    private final StatusCalculator calculator = new StatusCalculator();

    private final List<Cell> livingCells = new ArrayList<>();
    private final List<Cell> newLivingCells = new ArrayList<>();

    private final static long START_GENERATION = 1;
    private long currentGeneration;

    public Board(final long initialGeneration) {
        this.currentGeneration = Math.max(initialGeneration, START_GENERATION);
    }

    public Board() {
        this.currentGeneration = START_GENERATION;
    }

    public void nextRound() {

        calculateNextStatusOfCells(Status.ALIVE, livingCells);
        calculateNextStatusOfCells(Status.DEAD, determineDeadNeighbourCells());

        updateLivingCells();

        currentGeneration++;
    }

    private void calculateNextStatusOfCells(final Status currentStatus, final Collection<Cell> cells) {
        for (Cell cell : cells) {

            final List<Cell> neighbours = determineLivingNeighbourCells(cell);
            final Status newStatus = calculator.calculateStatus(currentStatus, neighbours.size());

            if (newStatus == Status.ALIVE) {
                newLivingCells.add(cell);
            }
        }
    }

    private List<Cell> determineLivingNeighbourCells(final Cell cell) {
        final List<Cell> livingNeighbours = new ArrayList<>(NEIGHBOUR_COUNT);
        for (Cell neighbour : getNeighbours(cell)) {
            if (cellIsAlive(neighbour)) {
                livingNeighbours.add(neighbour);
            }
        }
        return livingNeighbours;
    }

    private Set<Cell> determineDeadNeighbourCells() {
        final Set<Cell> deadNeighbours = new HashSet<>(livingCells.size());
        for (Cell cell : livingCells) {
            final List<Cell> neighbours = getNeighbours(cell);
            for (Cell neighbour : neighbours) {
                if (!cellIsAlive(neighbour)) {
                    deadNeighbours.add(neighbour);
                }
            }
        }
        return deadNeighbours;
    }

    private void updateLivingCells() {
        livingCells.clear();
        livingCells.addAll(newLivingCells);
        newLivingCells.clear();
    }

    public boolean cellIsAlive(final Cell cell) {
        return livingCells.contains(cell);
    }

    public Board add(final Cell cell) {
        livingCells.add(cell);
        return this;
    }

    public Board remove(final Cell cell) {
        livingCells.remove(cell);
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

    protected abstract List<Cell> getNeighbours(Cell cell);

    public List<Cell> getLivingCells() {
        return Collections.unmodifiableList(livingCells);
    }

    public int countCells() {
        return livingCells.size();
    }

    public long getCurrentGeneration() {
        return currentGeneration;
    }
}
