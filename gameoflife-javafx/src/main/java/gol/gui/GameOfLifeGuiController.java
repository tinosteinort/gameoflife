package gol.gui;

import gol.Cell;
import gol.board.*;
import gol.persistence.*;
import javafx.event.ActionEvent;
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

    private final ContextMenu contextMenu = new ContextMenu();
    private Optional<Cell> cellForContextMenu = Optional.empty();

    private final ConversionService conversionService;
    private final PersistenceService persistenceService;
    private final ResourceLoaderService resourceLoaderService;

    private BoardPainter boardPainter;

    private DialogSupport dialogSupport;

    private Board board;
    private StepTimer timer;

    public GameOfLifeGuiController() {
        this.conversionService = new ConversionService();
        this.persistenceService = new PersistenceService();
        this.resourceLoaderService = new ResourceLoaderService();
    }

    public void initController(final DialogSupport dialogSupport) {

        this.dialogSupport = dialogSupport;
        canvas = new ResizableCanvas();
        canvasHolder.getChildren().add(canvas);

        timer = new StepTimer(500, () -> doNextStep());

        initContextMenu();
        initBindings();
        initListener();

        calculateToolbarStatus();
    }

    private void initContextMenu() {
        final List<ResourceFigure> resourceFigures = resourceLoaderService.loadBuildInFigures();
        for (ResourceFigure resourceFigure : resourceFigures) {

            final MenuItem item = new MenuItem(resourceFigure.getName());
            contextMenu.getItems().add(item);

            item.setOnAction((ActionEvent event) -> {
                addResourceFigureToBoard(resourceFigure);
            });
        }
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
            contextMenu.hide();
            cellForContextMenu = Optional.empty();

            final Optional<Cell> clickedCell = getCellOnBoard(event);
            if (!clickedCell.isPresent()) {
                return;
            }

            if (event.isPopupTrigger()) {
                cellForContextMenu = clickedCell;
                contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
            }
            else {
                handleUserEditCell(clickedCell.get());
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

    private void addResourceFigureToBoard(final ResourceFigure figure) {
        if (!cellForContextMenu.isPresent()) {
            return;
        }

        final Cell userClickedCell = cellForContextMenu.get();

        for (Cell cell : figure.getCells()) {
            final Cell newCell = new Cell(userClickedCell.getX() + cell.getX(),
                                          userClickedCell.getY() + cell.getY());
            board.add(newCell);
        }

        paint();
    }

    private Optional<Cell> getCellOnBoard(final MouseEvent event) {
        if (!painterIsAvailable()) {
            return Optional.empty();
        }
        final Point2D boardPos = boardPainter.getPosOnBoard(event.getX(), event.getY());
        return  boardPainter.getCellAt(boardPos);
    }

    private void handleUserEditCell(final Cell clickedCell) {

        if (board.cellIsAlive(clickedCell)) {
            board.remove(clickedCell);
        }
        else {
            board.add(clickedCell);
        }

        paint();
    }

    public void paint() {

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (painterIsAvailable()) {
            boardPainter.paint(gc);
        }
    }

    private BoardPainter createAndBindPainter(final Board board) {
        boardPainter = new BoardPainterFactory().build(board, canvas.getWidth(), canvas.getHeight());
        boardPainter.setViewPortX(0);
        boardPainter.setViewPortY(0);
        boardPainter.viewPortWidthProperty().bind(canvas.widthProperty());
        boardPainter.viewPortHeightProperty().bind(canvas.heightProperty());
        return boardPainter;
    }

    private boolean painterIsAvailable() {
        return boardPainter != null;
    }

    private boolean boardIsEmpty() {
        if (board == null) {
            return true;
        }
        return board.countCells() == 0;
    }

    private boolean userPreventClearingOfBoard() {
        if (boardIsEmpty()) {
            return false;
        }
        final boolean discardBoard = dialogSupport.askUserForDiscardBoard();
        if (discardBoard) {
            return false;
        }
        return true;
    }

    private void clearBoard() {
        if (board != null) {
            board.clear();
        }
    }

    private void initBoard(final Board newBoard) {
        boardPainter = createAndBindPainter(newBoard);

        updateBoardInfos();
        calculateToolbarStatus();

        paint();
    }

    @FXML private void newFixedBoard() {
        if (userPreventClearingOfBoard()) {
            return;
        }

        final Optional<BoardBounds> bounds = dialogSupport.getBoundsFromUser();
        if (!bounds.isPresent()) {
            return;
        }

        clearBoard();

        board = new FixedBoard(bounds.get().getWidth(), bounds.get().getHeight());
        initBoard(board);
    }

    @FXML private void newTorusBoard() {
        if (userPreventClearingOfBoard()) {
            return;
        }

        final Optional<BoardBounds> bounds = dialogSupport.getBoundsFromUser();
        if (!bounds.isPresent()) {
            return;
        }

        clearBoard();

        board = new TorusBoard(bounds.get().getWidth(), bounds.get().getHeight());
        initBoard(board);
    }

    @FXML private void newEndlessBoard() {
        if (userPreventClearingOfBoard()) {
            return;
        }

        clearBoard();

        board = new EndlessBoard();
        initBoard(board);
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
        if (userPreventClearingOfBoard()) {
            return;
        }

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
        initBoard(board);
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
