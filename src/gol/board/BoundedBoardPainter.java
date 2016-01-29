package gol.board;

import gol.Cell;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Tino on 23.01.2016.
 */
public class BoundedBoardPainter implements BoardPainter {

    private final BoundedBoard board;

    private final IntegerProperty cellWidthProperty = new SimpleIntegerProperty(10);
    private final DoubleProperty xOffsetProperty = new SimpleDoubleProperty(0);
    private final DoubleProperty yOffsetProperty = new SimpleDoubleProperty(0);

    public BoundedBoardPainter(final BoundedBoard board) {
        this.board = board;
    }

    private double boardWidth() {
        return board.getWidth() * cellWidthProperty.get();
    }
    private double boardHeight() {
        return board.getHeight() * cellWidthProperty.get();
    }

    @Override
    public void paint(final GraphicsContext gc) {

        gc.setFill(Color.WHITE);
        gc.fillRect(xOffsetProperty.get(), yOffsetProperty.get(), boardWidth(), boardHeight());

        printGrid(gc, boardWidth(), boardHeight(), cellWidthProperty.doubleValue());

        for (Cell cell : board.getLivingCells()) {

            int cellX = cell.getX();
            int cellY = cell.getY();

            double xPos = xOffsetProperty.get() + (cellX * cellWidthProperty.get());
            double yPos = yOffsetProperty.get() + (cellY * cellWidthProperty.get());

            gc.setStroke(Color.BLACK);
            gc.setFill(Color.BLACK);
            gc.fillRect(xPos, yPos, cellWidthProperty.get(), cellWidthProperty.get());
        }
    }

    @Override
    public void navigateLeft() {
        xOffsetProperty.set(xOffsetProperty.get() - 10);
    }

    @Override
    public void navigateRight() {
        xOffsetProperty.set(xOffsetProperty.get() + 10);
    }

    @Override
    public void navigateUp() {
        yOffsetProperty.set(yOffsetProperty.get() - 10);
    }

    @Override
    public void navigateDown() {
        yOffsetProperty.set(yOffsetProperty.get() + 10);
    }

    private void printGrid(final GraphicsContext gc, double width, double height, double cellWith) {

        gc.setStroke(Color.LIGHTGRAY);

        for (double x = cellWith; x < width; x = x + cellWith) {

            gc.strokeLine(xOffsetProperty.get() + x, yOffsetProperty.get(), xOffsetProperty.get() + x, yOffsetProperty.get() + height);
        }

        for (double y = cellWith; y < height; y = y + cellWith) {

            gc.strokeLine(xOffsetProperty.get(), yOffsetProperty.get() + y, xOffsetProperty.get() + width, yOffsetProperty.get() + y);
        }
    }

    @Override
    public Point2D getPosOnBoard(double mouseX, double mouseY) {

        double x;
        if (xOffsetProperty.get() >= 0) {
            double diff = mouseX - xOffsetProperty.get();
            x = diff;
        }
        else {
            x = Math.abs(xOffsetProperty.get()) + mouseX;
        }

        double y;
        if (yOffsetProperty.get() >= 0) {
            double diff = mouseY - yOffsetProperty.get();
            y = diff;
        }
        else {
            y = Math.abs(yOffsetProperty.get()) + mouseY;
        }

        if (x < 0 || x >= boardWidth() ||
                y < 0 || y >= boardHeight()) {
            return null;
        }

        return new Point2D(x, y);
    }

    @Override
    public Point2D getFieldAt(final Point2D pointOnBoard) {
        if (pointOnBoard.getX() < 0 || pointOnBoard.getX() >= boardWidth() ||
                pointOnBoard.getY() < 0 || pointOnBoard.getY() >= boardHeight()) {
            return null;
        }

        return new Point2D(pointOnBoard.getX() / cellWidthProperty.get(), pointOnBoard.getY() / cellWidthProperty.get());
    }

    public IntegerProperty cellWithProperty() {
        return cellWidthProperty;
    }
}
