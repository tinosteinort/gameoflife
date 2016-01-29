package gol.board;

import gol.Cell;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by Tino on 28.01.2016.
 */
public class EndlessBoardPainter implements BoardPainter {

    private final EndlessBoard board;
    private final double canvasWidth;
    private final double canvasHeight;

    private final IntegerProperty cellWidthProperty = new SimpleIntegerProperty(10);

    public EndlessBoardPainter(final EndlessBoard board, final double canvasWidth, final double canvasHeight) {
        this.board = board;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    @Override
    public void paint(final GraphicsContext gc) {

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        printGrid(gc, canvasWidth, canvasHeight, cellWidthProperty.doubleValue());

        for (Cell cell : board.getLivingCells()) {

            int cellX = cell.getX();
            int cellY = cell.getY();

            double xPos = cellX * cellWidthProperty.get();
            double yPos = cellY * cellWidthProperty.get();

            gc.setStroke(Color.BLACK);
            gc.setFill(Color.BLACK);
            gc.fillRect(xPos, yPos, cellWidthProperty.get(), cellWidthProperty.get());
        }
    }

    private void printGrid(final GraphicsContext gc, double width, double height, double cellWith) {

        gc.setStroke(Color.LIGHTGRAY);

        for (double x = cellWith; x < width; x = x + cellWith) {

            gc.strokeLine(x, 0, x, height);
        }

        for (double y = cellWith; y < height; y = y + cellWith) {

            gc.strokeLine(0, y, width, y);
        }
    }

    @Override
    public IntegerProperty cellWithProperty() {
        return cellWidthProperty;
    }

    @Override
    public Point2D getPosOnBoard(double mouseX, double mouseY) {
        return null;
    }

    @Override
    public Point2D getFieldAt(Point2D pointOnBoard) {
        return null;
    }

    @Override
    public void navigateLeft() {

    }

    @Override
    public void navigateRight() {

    }

    @Override
    public void navigateUp() {

    }

    @Override
    public void navigateDown() {

    }
}
