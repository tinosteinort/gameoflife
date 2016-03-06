package gol.gui;

import gol.Cell;
import gol.board.Board;
import gol.board.BoardPainter;
import gol.board.BoardPainterFactory;
import gol.board.FixedBoard;
import gol.persistence.ResourceFigure;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * Created by Tino on 06.03.2016.
 */
public class DirectionDialogGui {

    @FXML private Canvas canvas;
    @FXML private Button leftBtn;
    @FXML private Button rightBtn;
    @FXML private Button upBtn;
    @FXML private Button downBtn;

    private Board board;
    private BoardPainter boardPainter;

    private ResourceFigure figure;

    public void initController() {
        initBoard();
        refreshFigure();
    }

    private void initBoard() {
        final BoardBounds bounds = getBoundsFromFigure(figure);
        board = new FixedBoard(bounds.getWidth(), bounds.getHeight());
        boardPainter = new BoardPainterFactory().build(board, canvas.getWidth(), canvas.getHeight());
        boardPainter.setViewPortX(0);
        boardPainter.setViewPortY(0);
        boardPainter.viewPortWidthProperty().bind(canvas.widthProperty());
        boardPainter.viewPortHeightProperty().bind(canvas.heightProperty());
    }

    @FXML private void mirrorLeft() {

    }

    @FXML private void mirrorRight() {

    }

    @FXML private void mirrorUp() {

    }

    @FXML private void mirrorDown() {

    }

    private BoardBounds getBoundsFromFigure(final ResourceFigure figure) {
        int maxWidth = 0;
        int maxHeight = 0;

        for (Cell cell : figure.getCells()) {
            maxWidth = Math.max(maxWidth, cell.getX() + 1);
            maxHeight = Math.max(maxHeight, cell.getY() + 1);
        }

        return new BoardBounds(maxWidth, maxHeight);
    }

    private void refreshFigure() {
        board.clear();
        board.addAll(figure.getCells());

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
        return figure;
    }

    public void setFigureToEdit(final ResourceFigure figureToEdit) {
        this.figure = copy(figureToEdit);
    }
}
