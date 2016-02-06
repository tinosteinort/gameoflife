package gol.board;

import gol.Cell;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * Created by Tino on 23.01.2016.
 */
public class BoundedBoardPainter implements BoardPainter {

    private static final int CELL_WIDTH_THRESHOLD = 2;

    private final BoundedBoard board;

    private final IntegerProperty cellWidthProperty = new SimpleIntegerProperty(ViewPort.DEFAULT_CELL_WIDTH);

    private Color backgroundColor = Color.GRAY;
    private Color boardBackgroundColor = Color.WHITE;
    private Color gridLineColor = Color.LIGHTGRAY;
    private Color cellColor = Color.BLACK;

    private final ViewPort viewPort;

    public BoundedBoardPainter(final BoundedBoard board, final double canvasWidth, final double canvasHeight) {
        this.board = board;
        this.viewPort = new ViewPort(canvasWidth, canvasHeight);
        this.viewPort.cellWidthPropertyProperty().bind(cellWidthProperty);
    }

    public double boardWidthInPixel() {
        return board.getWidth() * cellWidthProperty.get();
    }
    public double boardHeightInPixel() {
        return board.getHeight() * cellWidthProperty.get();
    }

    @Override
    public void paint(final GraphicsContext gc) {

        final double boardWidth = boardWidthInPixel();
        final double boardHeight = boardHeightInPixel();
        final double cellWidth = cellWidthProperty.doubleValue();

        // Fill Background
        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, boardWidth, boardHeight);

        final double viewPortX = viewPort.viewPortXinPixel();
        final double viewPortY = viewPort.viewPortYinPixel();
        final double viewPortWidth = viewPort.getViewPortWidth();
        final double viewPortHeight = viewPort.getViewPortHeight();

        // Check if Board is in ViewPort
        if (viewPortX + viewPortWidth < 0) {
            return;
        }
        if (viewPortX > boardWidth) {
            return;
        }
        if (viewPortY + viewPortHeight < 0) {
            return;
        }
        if (viewPortY > boardHeight) {
            return;
        }

        // Calculate Corners of the visible Part of the Board
        final double leftX;
        if (viewPortX < 0) {
            leftX = -viewPortX;
        }
        else {
            leftX = 0;
        }

        final double topY;
        if (viewPortY < 0) {
            topY = -viewPortY;
        }
        else {
            topY = 0;
        }

        final double rightX;
        if (boardWidth > (viewPortX + viewPortWidth)) {
            rightX = viewPortWidth;
        }
        else {
            rightX = boardWidth - viewPortX;
        }

        final double bottomY;
        if (boardHeight > (viewPortY + viewPortHeight)) {
            bottomY = viewPortHeight;
        }
        else {
            bottomY = boardHeight - viewPortY;
        }

        // Paint Background of the Grid
        gc.setFill(boardBackgroundColor);
        gc.fillRect(leftX, topY, rightX - leftX, bottomY - topY);

        circle(gc, leftX, topY);
        circle(gc, rightX, topY);
        circle(gc, leftX, bottomY);
        circle(gc, rightX, bottomY);

        // Paint Grid Lines
        if (cellWidth > CELL_WIDTH_THRESHOLD) {
            gc.setStroke(gridLineColor);
            for (double x = leftX; x <= rightX; x += cellWidth) {
                gc.strokeLine(x, topY, x, bottomY);
            }
            for (double y = topY; y <= bottomY; y += cellWidth) {
                gc.strokeLine(leftX, y, rightX, y);
            }
        }

        // Paint the Cells
        gc.setStroke(cellColor);
        gc.setFill(cellColor);
        for (Cell cell : board.getLivingCells()) {

            if (viewPort.cellIsInViewPort(cell)) {

                final int cellX = cell.getX();
                final int cellY = cell.getY();

                final double xPos = -viewPortX + (cellX * cellWidthProperty.get());
                final double yPos = -viewPortY + (cellY * cellWidthProperty.get());

                gc.fillRect(xPos, yPos, cellWidthProperty.get(), cellWidthProperty.get());
            }
        }
    }

    private void circle(final GraphicsContext gc, final double x, final double y) {
        final double width = 10;
        gc.setStroke(Color.RED);
        gc.strokeOval(x - (width / 2), y - (width / 2), width, width);
    }

    @Override
    public Point2D getPosOnBoard(final double mouseX, final double mouseY) {
        return new Point2D(mouseX + viewPort.viewPortXinPixel(), mouseY + viewPort.viewPortYinPixel());
    }

    @Override
    public Optional<Cell> getCellAt(final Point2D pointOnBoard) {
        if (pointOnBoard.getX() < 0 || pointOnBoard.getX() >= boardWidthInPixel() ||
                pointOnBoard.getY() < 0 || pointOnBoard.getY() >= boardHeightInPixel()) {
            return Optional.empty();
        }

        return Optional.of(new Cell((int) (pointOnBoard.getX() / cellWidthProperty.get()),
                                    (int) (pointOnBoard.getY() / cellWidthProperty.get())));
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

    public IntegerProperty cellWithProperty() {
        return cellWidthProperty;
    }
}
