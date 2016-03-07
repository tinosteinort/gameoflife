package gol.gui;

import gol.Cell;
import gol.board.*;
import gol.persistence.ResourceFigure;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tino on 06.03.2016.
 */
public class DirectionDialogGui {

    @FXML private Canvas canvas;
    @FXML private Button leftRightBtn;
    @FXML private Button upDownBtn;

    private Board board;
    private BoardPainter boardPainter;

    private ResourceFigure figure;

    public void initController(final ResourceFigure figureToEdit) {
        this.figure = copy(figureToEdit);

        initBoard();
        refreshFigure();
    }

    private void initBoard() {
        board = new EndlessBoard();
        boardPainter = new BoardPainterFactory().build(board, canvas.getWidth(), canvas.getHeight());
        boardPainter.setViewPortX(0);
        boardPainter.setViewPortY(0);
        boardPainter.viewPortWidthProperty().bind(canvas.widthProperty());
        boardPainter.viewPortHeightProperty().bind(canvas.heightProperty());
    }

    @FXML private void mirrorLeftRight() {
        final ResourceFigure mirroredFigure = new ResourceFigure(figure.getName());

        for (Cell cell : figure.getCells()) {
            final Cell mirroredXCell = new Cell(-cell.getX(), cell.getY());
            mirroredFigure.getCells().add(mirroredXCell);
        }

        figure = mirroredFigure;

        refreshFigure();
    }

    @FXML private void mirrorUpDown() {
        final ResourceFigure mirroredFigure = new ResourceFigure(figure.getName());

        for (Cell cell : figure.getCells()) {
            final Cell mirroredXCell = new Cell(cell.getX(), -cell.getY());
            mirroredFigure.getCells().add(mirroredXCell);
        }

        figure = mirroredFigure;

        refreshFigure();
    }

    private int findMinX(final List<Cell> cells) {
        int minX = 0;
        for (Cell cell : cells) {
            minX = Math.min(minX, cell.getX());
        }
        return minX;
    }

    private int findMinY(final List<Cell> cells) {
        int minY = 0;
        for (Cell cell : cells) {
            minY = Math.min(minY, cell.getY());
        }
        return minY;
    }

    private void refreshFigure() {
        board.clear();
        board.addAll(figure.getCells());

        final int minX = findMinX(figure.getCells());
        final int minY = findMinY(figure.getCells());
        boardPainter.setViewPortX(minX);
        boardPainter.setViewPortY(minY);

        paint();
    }

    public void paint() {

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        boardPainter.paint(gc);
    }

    private ResourceFigure copy(final ResourceFigure figure) {
        final ResourceFigure copy = new ResourceFigure(figure.getName());
        copy.getCells().addAll(figure.getCells());
        return copy;
    }

    public ResourceFigure getCurrentResourceFigure() {

        final List<Cell> normalisedCells = moveToPositionZero(figure.getCells());
        figure.getCells().clear();
        figure.getCells().addAll(normalisedCells);

        return figure;
    }

    private List<Cell> moveToPositionZero(final List<Cell> cells) {
        int minX = findMinX(figure.getCells());
        int minY = findMinY(figure.getCells());

        minX = (minX > 0 ? 0 : minX);
        minY = (minY > 0 ? 0 : minY);

        final List<Cell> normalisedCells = new ArrayList<>(cells.size());
        for (Cell cell : figure.getCells()) {
            final Cell normalizedCell = new Cell(cell.getX() + Math.abs(minX),
                    cell.getY() + Math.abs(minY));
            normalisedCells.add(normalizedCell);
        }

        return normalisedCells;
    }
}
