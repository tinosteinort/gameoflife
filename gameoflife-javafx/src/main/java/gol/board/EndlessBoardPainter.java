package gol.board;

import gol.Cell;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
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

    private Color boardBackgroundColor = Color.WHITE;
    private Color gridLineColor = Color.LIGHTGRAY;
    private Color cellColor = Color.BLACK;

    private final ViewPort viewPort;

    public EndlessBoardPainter(final EndlessBoard board, final double canvasWidth, final double canvasHeight) {
        this.board = board;
        this.viewPort = new ViewPort(canvasWidth, canvasHeight);
    }

    @Override
    public void paint(final GraphicsContext gc) {

        final double viewPortWidth = viewPort.viewPortWidthProperty().get();
        final double viewPortHeight = viewPort.viewPortHeightProperty().get();

        // Paint Background of the Grid
        gc.setFill(boardBackgroundColor);
        gc.fillRect(0, 0, viewPortWidth, viewPortHeight);

        final double cellWidth = viewPort.cellWidthPropertyProperty().doubleValue();

        // Paint Grid Lines
        if (cellWidth > CELL_WIDTH_THRESHOLD) {
            gc.setStroke(gridLineColor);
            for (double x = cellWidth; x < viewPortWidth; x = x + cellWidth) {
                gc.strokeLine(x, 0, x, viewPortHeight);
            }
            for (double y = cellWidth; y < viewPortHeight; y = y + cellWidth) {
                gc.strokeLine(0, y, viewPortWidth, y);
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
    public Point2D getPosOnBoard(final double mouseX, final double mouseY) {
        return new Point2D(mouseX + viewPort.viewPortXinPixel(), mouseY + viewPort.viewPortYinPixel());
    }

    @Override
    public Optional<Cell> getCellAt(final Point2D pointOnBoard) {
        // Works, but somehow creepy. There is a better way.
        final int x = (int) (pointOnBoard.getX() < 0 ? pointOnBoard.getX() - 9 : pointOnBoard.getX());
        final int y = (int) (pointOnBoard.getY() < 0 ? pointOnBoard.getY() - 9 : pointOnBoard.getY());
        return Optional.of(new Cell(x / viewPort.cellWidthPropertyProperty().get(),
                                    y / viewPort.cellWidthPropertyProperty().get()));
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

    @Override
    public DoubleProperty viewPortWidthProperty() {
        return viewPort.viewPortWidthProperty();
    }
    @Override
    public DoubleProperty viewPortHeightProperty() {
        return viewPort.viewPortHeightProperty();
    }
    @Override
    public IntegerProperty cellWidthProperty() {
        return viewPort.cellWidthPropertyProperty();
    }
}
