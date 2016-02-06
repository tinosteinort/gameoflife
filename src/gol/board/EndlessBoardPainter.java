package gol.board;

import gol.Cell;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * Created by Tino on 28.01.2016.
 */
public class EndlessBoardPainter implements BoardPainter {

    private static final int CELL_WIDTH_THRESHOLD = 2;

    private final EndlessBoard board;
    private final double canvasWidth;
    private final double canvasHeight;

    private final IntegerProperty cellWidthProperty = new SimpleIntegerProperty(ViewPort.DEFAULT_CELL_WIDTH);

    private Color boardBackgroundColor = Color.WHITE;
    private Color gridLineColor = Color.LIGHTGRAY;
    private Color cellColor = Color.BLACK;

    private final ViewPort viewPort;

    public EndlessBoardPainter(final EndlessBoard board, final double canvasWidth, final double canvasHeight) {
        this.board = board;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.viewPort = new ViewPort(canvasWidth, canvasHeight);
        this.viewPort.cellWidthPropertyProperty().bind(cellWidthProperty);
    }

    @Override
    public void paint(final GraphicsContext gc) {

        // Paint Background of the Grid
        gc.setFill(boardBackgroundColor);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);

        final double cellWidth = cellWidthProperty.doubleValue();

        // Paint Grid Lines
        if (cellWidth > CELL_WIDTH_THRESHOLD) {
            gc.setStroke(gridLineColor);
            for (double x = cellWidth; x < canvasWidth; x = x + cellWidth) {
                gc.strokeLine(x, 0, x, canvasHeight);
            }
            for (double y = cellWidth; y < canvasHeight; y = y + cellWidth) {
                gc.strokeLine(0, y, canvasWidth, y);
            }
        }

        // Paint the Cells
        gc.setStroke(cellColor);
        gc.setFill(cellColor);
        for (Cell cell : board.getLivingCells()) {

            if (viewPort.cellIsInViewPort(cell)) {

                final int cellXInViewPort = cell.getX() - viewPort.getViewPortX();
                final int cellYInViewPort = cell.getY() - viewPort.getViewPortY();

                final double xPos = cellXInViewPort * cellWidth;
                final double yPos = cellYInViewPort * cellWidth;

                gc.fillRect(xPos, yPos, cellWidth, cellWidth);
            }
        }
    }

    @Override
    public IntegerProperty cellWithProperty() {
        return cellWidthProperty;
    }

    @Override
    public Point2D getPosOnBoard(final double mouseX, final double mouseY) {
        return new Point2D(mouseX + viewPort.viewPortXinPixel(), mouseY + viewPort.viewPortYinPixel());
    }

    @Override
    public Optional<Cell> getCellAt(final Point2D pointOnBoard) {
        // Works, but somehow creepy. There is a better way.
        final int x = (int) (pointOnBoard.getX() < 0 ? pointOnBoard.getX() - 9 : pointOnBoard.getX());
        final int y = (int) (pointOnBoard.getY() < 0 ? pointOnBoard.getY() - 9 : pointOnBoard.getY());
        return Optional.of(new Cell(x / cellWidthProperty.get(), y / cellWidthProperty.get()));
    }

    @Override
    public void setViewPortX(final int x) {
        viewPort.setViewPortX(x);
    }

    @Override
    public void setViewPortY(final int y) {
        viewPort.setViewPortY(y);
    }

    @Override
    public int getViewPortX() {
        return viewPort.getViewPortX();
    }

    @Override
    public int getViewPortY() {
        return viewPort.getViewPortY();
    }
}
