package gol.gui;

import gol.Cell;
import gol.board.*;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Tino on 21.01.2016.
 */
public class GameOfLifeGuiController {

    @FXML private Button openBtn;
    @FXML private MenuButton newBtn;
    @FXML private MenuItem newBoundedFieldItem;
    @FXML private MenuItem newTorusFieldItem;
    @FXML private MenuItem newEndlessFieldItem;
    @FXML private Button saveBtn;

    @FXML Pane canvasHolder;
    @FXML private Canvas canvas;

    @FXML private Button nextStepBtn;
    @FXML private Button playBtn;
    @FXML private Button pauseBtn;
    @FXML private Label generationLabel;
    @FXML private Slider durationSlider;
    @FXML private Button leftBtn;
    @FXML private Button rightBtn;
    @FXML private Button upBtn;
    @FXML private Button downBtn;

    private BoardPainter boardPainter;

    private Board board;
    private StepTimer timer;

    public GameOfLifeGuiController() {

    }

    public void initController(final Scene scene) {

        canvas = new ResizableCanvas();
        canvasHolder.getChildren().add(canvas);

        timer = new StepTimer(500, () -> doNextStep());

        initBindings();
        initListener();

        calculateToolbarStatus();
    }

    private void initBindings() {
        durationSlider.valueProperty().bindBidirectional(timer.stepDurationProperty());

        canvas.widthProperty().bind(canvasHolder.widthProperty());
        canvas.heightProperty().bind(canvasHolder.heightProperty());
        canvas.widthProperty().addListener(event -> paint());
        canvas.heightProperty().addListener(event -> paint());
    }

    private void initListener() {

        canvas.setOnMouseClicked((MouseEvent event) -> {

            final Point2D boardPos = boardPainter.getPosOnBoard(event.getX(), event.getY());
            final Optional<Cell> clickedCell = boardPainter.getCellAt(boardPos);

            if (clickedCell.isPresent()) {

                final Cell cell = clickedCell.get();
                if (board.cellIsAlive(cell)) {
                    board.remove(cell);
                }
                else {
                    board.add(cell);
                }

                paint();
            }
        });

        canvas.setOnScroll((ScrollEvent event) -> {

            if (event.getDeltaY() < 0) {
                final int newWidth = boardPainter.cellWidthProperty().get() - 1;
                if (newWidth > 0) {
                    boardPainter.cellWidthProperty().set(newWidth);
                }
            }
            else {
                final int newWidth = boardPainter.cellWidthProperty().get() + 1;
                boardPainter.cellWidthProperty().set(newWidth);
            }

            paint();
        });
    }

    private void initFigures() {

        board.clear();

        List<Cell> blinker = Arrays.asList(new Cell(5, 5), new Cell(6, 5), new Cell(7, 5));
        board.addAll(blinker);

        List<Cell> gleiter = Arrays.asList(new Cell(5, 10), new Cell(6, 10), new Cell(7, 10), new Cell(7, 9), new Cell(6, 8));
        board.addAll(gleiter);

        List<Cell> lwss = Arrays.asList(new Cell(20, 10), new Cell(21, 10), new Cell(22, 10), new Cell(23, 10), new Cell(19, 11), new Cell(23, 11), new Cell(23, 12),new Cell(19, 13), new Cell(22, 13));
        board.addAll(lwss);

//        board.clear();

//        board.add(new Cell(5, 5)).add(new Cell(6, 5)).add(new Cell(7, 5));
    }

    public void paint() {

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        final double width = canvas.getWidth();
        final double height = canvas.getHeight();

        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, width, height);

        if (boardPainter != null) {
            boardPainter.paint(gc);
        }

        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeRect(0, 0, width, height);

        gc.strokeLine(0, 0, width, height);
        gc.strokeLine(0, height, width, 0);
    }

    @FXML private void newBoundedBoard() {
        if (board != null) {
            board.clear();
        }
        board = new FixedBoard(50, 20);
        boardPainter = new BoundedBoardPainter((BoundedBoard) board, canvas.getWidth(), canvas.getHeight());
        boardPainter.setViewPortX(0);
        boardPainter.setViewPortY(0);
        boardPainter.viewPortWidthProperty().bind(canvas.widthProperty());
        boardPainter.viewPortHeightProperty().bind(canvas.heightProperty());

        initFigures();
        updateBoardInfos();

        paint();
    }

    @FXML private void newTorusBoard() {
        if (board != null) {
            board.clear();
        }
        board = new TorusBoard(50, 20);
        boardPainter = new BoundedBoardPainter((BoundedBoard) board, canvas.getWidth(), canvas.getHeight());
        boardPainter.setViewPortX(0);
        boardPainter.setViewPortY(0);
        boardPainter.viewPortWidthProperty().bind(canvas.widthProperty());
        boardPainter.viewPortHeightProperty().bind(canvas.heightProperty());

        initFigures();
        updateBoardInfos();

        paint();
    }

    @FXML private void newEndlessBoard() {
        if (board != null) {
            board.clear();
        }
        board = new EndlessBoard();
        boardPainter = new EndlessBoardPainter((EndlessBoard) board, canvas.getWidth(), canvas.getHeight());
        boardPainter.setViewPortX(0);
        boardPainter.setViewPortY(0);
        boardPainter.viewPortWidthProperty().bind(canvas.widthProperty());
        boardPainter.viewPortHeightProperty().bind(canvas.heightProperty());

        initFigures();
        updateBoardInfos();

        paint();
    }

    @FXML private void doSave() {

    }

    @FXML private void doNextStep() {
        board.nextRound();
        updateBoardInfos();

        paint();
    }

    private void updateBoardInfos() {
        generationLabel.setText(String.valueOf(board.getCurrentGeneration()));
    }

    @FXML private void doPlay() {
        timer.start();
        calculateToolbarStatus();
    }

    @FXML private void doPause() {
        timer.stop();
        calculateToolbarStatus();
    }

    @FXML private void navigateLeft() {
        boardPainter.setViewPortX(boardPainter.getViewPortX() - 1);
        paint();
    }
    @FXML private void navigateRight() {
        boardPainter.setViewPortX(boardPainter.getViewPortX() + 1);
        paint();
    }
    @FXML private void navigateUp() {
        boardPainter.setViewPortY(boardPainter.getViewPortY() - 1);
        paint();
    }
    @FXML private void navigateDown() {
        boardPainter.setViewPortY(boardPainter.getViewPortY() + 1);
        paint();
    }

    private void calculateToolbarStatus() {
        openBtn.setDisable(timer.isRunning());
        newBtn.setDisable(timer.isRunning());
        saveBtn.setDisable(timer.isRunning());
        nextStepBtn.setDisable(timer.isRunning());
        playBtn.setDisable(timer.isRunning());
        pauseBtn.setDisable(!timer.isRunning());
    }
}
