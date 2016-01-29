package gol.gui;

import gol.Cell;
import gol.board.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tino on 21.01.2016.
 */
public class GameOfLifeGuiController {

    @FXML private ComboBox boardBox;
    @FXML private Canvas canvas;

    @FXML private Button nextStepBtn;
    @FXML private Button clearBtn;
    @FXML private Button playBtn;
    @FXML private Button pauseBtn;
    @FXML private Label generationLabel;
    @FXML private Slider durationSlider;
    @FXML private Button leftBtn;
    @FXML private Button rightBtn;
    @FXML private Button upBtn;
    @FXML private Button downBtn;

    private BoardPainter boardPainter;

    private final ObservableList<String> boards = FXCollections.observableArrayList();

    private Board board;
    private StepTimer timer;

    public GameOfLifeGuiController() {

        boards.addAll("Fixed Board", "Torus Board", "Endless Board");
    }

    public void initController(Scene scene) {

        timer = new StepTimer(100, () -> doNextStep());

        durationSlider.valueProperty().bindBidirectional(timer.stepDurationProperty());

        boardBox.setItems(boards);
        boardBox.getSelectionModel().selectFirst();

        selectBoard((String) boardBox.getSelectionModel().getSelectedItem());

//        board = new FixedBoard(50, 20);
//        board = new TorusBoard(50, 20);
//        boardPainter = new BoundedBoardPainter((BoundedBoard) board);

        initFigures();

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Point2D boardPos = boardPainter.getPosOnBoard(event.getX(), event.getY());
                System.out.println(boardPos);

                if (boardPos != null) {

                    Point2D fieldPos = boardPainter.getFieldAt(boardPos);
                    if (fieldPos != null) {

                        Cell cell = new Cell((int) fieldPos.getX(), (int) fieldPos.getY());
                        if (board.cellIsAlive(cell)) {
                            board.remove(cell);
                        }
                        else {
                            board.add(cell);
                        }

                        paint();
                    }
                }
            }
        });

        canvas.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() < 0) {
                    final int newWidth = boardPainter.cellWithProperty().get() - 1;
                    if (newWidth > 0) {
                        boardPainter.cellWithProperty().set(newWidth);
                    }
                }
                else {
                    final int newWidth = boardPainter.cellWithProperty().get() + 1;
                    boardPainter.cellWithProperty().set(newWidth);
                }

                paint();
            }
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

//        board.add(new Cell(5, 5)).add(new Cell(6, 5)).add(new Cell(7, 5));
    }

    public void paint() {

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        double width = canvas.getWidth();
        double height = canvas.getHeight();

        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, width, height);

        double cellWith = 10;

        boardPainter.paint(gc);
    }

    @FXML private void itemSelected(final ActionEvent event) {

        final String selection = (String) boardBox.getSelectionModel().getSelectedItem();

        selectBoard(selection);

        initFigures();

        paint();
    }

    private void selectBoard(final String boardId) {

        switch (boardId) {
            case "Fixed Board":
                if (board != null) {
                    board.clear();
                }
                board = new FixedBoard(50, 20);
                boardPainter = new BoundedBoardPainter((BoundedBoard) board);
                break;
            case "Torus Board":
                if (board != null) {
                    board.clear();
                }
                board = new TorusBoard(50, 20);
                boardPainter = new BoundedBoardPainter((BoundedBoard) board);
                break;
            case "Endless Board":
                if (board != null) {
                    board.clear();
                }
                board = new EndlessBoard();
                boardPainter = new EndlessBoardPainter((EndlessBoard) board, canvas.getWidth(), canvas.getHeight());
                break;
        }

        paint();
    }

    @FXML private void doNextStep() {

        board.nextRound();
        generationLabel.setText(String.valueOf(board.getCurrentGeneration()));

        paint();
    }

    @FXML private void doClear() {
        initFigures();

        paint();
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
        boardPainter.navigateLeft();
        paint();
    }
    @FXML private void navigateRight() {
        boardPainter.navigateRight();
        paint();
    }
    @FXML private void navigateUp() {
        boardPainter.navigateUp();
        paint();
    }
    @FXML private void navigateDown() {
        boardPainter.navigateDown();
        paint();
    }

    private void calculateToolbarStatus() {

        boardBox.setDisable(timer.isRunning());
        nextStepBtn.setDisable(timer.isRunning());
        clearBtn.setDisable(timer.isRunning());
        playBtn.setDisable(timer.isRunning());
        pauseBtn.setDisable(!timer.isRunning());
    }
}
