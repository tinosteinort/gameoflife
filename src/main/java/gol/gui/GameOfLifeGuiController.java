package gol.gui;

import gol.Cell;
import gol.board.*;
import gol.persistence.ConversionService;
import gol.persistence.PersistenceService;
import gol.persistence.XmlGameOfLifeState;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Tino on 21.01.2016.
 */
public class GameOfLifeGuiController {

    private final static int NAVIGATION_STEP_SIZE = 5;

    @FXML private Button openBtn;
    @FXML private MenuButton newBtn;
    @FXML private MenuItem newBoundedFieldItem;
    @FXML private MenuItem newTorusFieldItem;
    @FXML private MenuItem newEndlessFieldItem;
    @FXML private Button saveBtn;

    @FXML Pane canvasHolder;
    private Canvas canvas;

    @FXML private Button nextStepBtn;
    @FXML private Button playBtn;
    @FXML private Button pauseBtn;
    @FXML private Label generationLabel;
    @FXML private Slider durationSlider;
    @FXML private Button leftBtn;
    @FXML private Button rightBtn;
    @FXML private Button upBtn;
    @FXML private Button downBtn;

    private final ConversionService conversionService;
    private final PersistenceService persistenceService;

    private BoardPainter boardPainter;

    private DialogSupport dialogSupport;

    private Board board;
    private StepTimer timer;

    public GameOfLifeGuiController() {
        this.conversionService = new ConversionService();
        this.persistenceService = new PersistenceService();
    }

    public void initController(final DialogSupport dialogSupport) {

        this.dialogSupport = dialogSupport;
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
            if (!painterIsAvailable()) {
                return;
            }

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
            if (!painterIsAvailable()) {
                return;
            }

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

        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (painterIsAvailable()) {
            boardPainter.paint(gc);
        }
    }

    private boolean painterIsAvailable() {
        return boardPainter != null;
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
        calculateToolbarStatus();

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
        calculateToolbarStatus();

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
        calculateToolbarStatus();

        paint();
    }

    @FXML private void doSave() {
        final Optional<Path> fileOption = dialogSupport.selectPathForSave();
        if (!fileOption.isPresent()) {
            return;
        }

        saveGameState(fileOption.get());
    }

    private void saveGameState(final Path file) {
        final XmlGameOfLifeState gameState = conversionService.convert(board);
        persistenceService.save(file, gameState);
    }

    @FXML private void doOpen() {
        final Optional<Path> fileOption = dialogSupport.selectPathForOpen();
        if (!fileOption.isPresent()) {
            return;
        }

        loadGameState(fileOption.get());
    }

    private void loadGameState(final Path file) {
        if (board != null) {
            board.clear();
        }

        final XmlGameOfLifeState gameState = persistenceService.load(file);
        board = conversionService.convert(gameState);

        boardPainter = new BoardPainterFactory().build(board, canvas.getWidth(), canvas.getHeight());
        boardPainter.setViewPortX(0);
        boardPainter.setViewPortY(0);
        boardPainter.viewPortWidthProperty().bind(canvas.widthProperty());
        boardPainter.viewPortHeightProperty().bind(canvas.heightProperty());

        updateBoardInfos();
        calculateToolbarStatus();

        paint();
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
        boardPainter.setViewPortX(boardPainter.getViewPortX() - NAVIGATION_STEP_SIZE);
        paint();
    }
    @FXML private void navigateRight() {
        boardPainter.setViewPortX(boardPainter.getViewPortX() + NAVIGATION_STEP_SIZE);
        paint();
    }
    @FXML private void navigateUp() {
        boardPainter.setViewPortY(boardPainter.getViewPortY() - NAVIGATION_STEP_SIZE);
        paint();
    }
    @FXML private void navigateDown() {
        boardPainter.setViewPortY(boardPainter.getViewPortY() + NAVIGATION_STEP_SIZE);
        paint();
    }

    private void calculateToolbarStatus() {
        openBtn.setDisable(timer.isRunning());
        newBtn.setDisable(timer.isRunning());
        saveBtn.setDisable(timer.isRunning() || !painterIsAvailable());
        nextStepBtn.setDisable(timer.isRunning() || !painterIsAvailable());
        playBtn.setDisable(timer.isRunning() || !painterIsAvailable());
        pauseBtn.setDisable(!timer.isRunning() || !painterIsAvailable());
        leftBtn.setDisable(!painterIsAvailable());
        rightBtn.setDisable(!painterIsAvailable());
        upBtn.setDisable(!painterIsAvailable());
        downBtn.setDisable(!painterIsAvailable());
    }
}
