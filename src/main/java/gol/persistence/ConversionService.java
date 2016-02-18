package gol.persistence;

import gol.Cell;
import gol.board.*;

import java.util.stream.Collectors;

/**
 * Created by Tino on 18.02.2016.
 */
public class ConversionService {

    public XmlGameOfLifeState convert(final Board board) {
        final XmlGameOfLifeState gameState = new XmlGameOfLifeState();

        final XmlBoardType boardType = determineType(board);
        gameState.setBoardType(boardType);

        switch (boardType) {
            case FIXED:
            case TORUS:
                gameState.setBoardWidth(((BoundedBoard) board).getWidth());
                gameState.setBoardHeight(((BoundedBoard) board).getHeight());
                break;
        }

        gameState.setGeneration(board.getCurrentGeneration());
        gameState.getCells().addAll(board.getLivingCells().stream()
                                                            .map(this::convert)
                                                            .collect(Collectors.toSet()));
        return gameState;
    }

    private XmlBoardType determineType(final Board board) {
        if (board instanceof FixedBoard) {
            return XmlBoardType.FIXED;
        }
        else if (board instanceof TorusBoard) {
            return XmlBoardType.TORUS;
        }
        else if (board instanceof EndlessBoard) {
            return XmlBoardType.ENDLESS;
        }
        throw new IllegalStateException("Board not available");
    }

    private XmlCell convert(final Cell cell) {
        final XmlCell xmlCell = new XmlCell();
        xmlCell.setX(cell.getX());
        xmlCell.setY(cell.getY());
        return xmlCell;
    }

    public Board convert(final XmlGameOfLifeState gameState) {

        final Board board;
        switch (gameState.getBoardType()) {
            case FIXED:
                board = new FixedBoard(gameState.getGeneration(),
                                       gameState.getBoardWidth(),
                                       gameState.getBoardHeight());
                break;
            case TORUS:
                board = new TorusBoard(gameState.getGeneration(),
                                       gameState.getBoardWidth(),
                                       gameState.getBoardHeight());
                break;
            case ENDLESS:
                board = new EndlessBoard(gameState.getGeneration());
                break;
            default:
                throw new IllegalArgumentException("Invalid Board Type");
        }

        board.addAll(gameState.getCells().stream()
                                            .map(this::convert)
                                            .collect(Collectors.toSet()));
        return board;
    }

    private Cell convert(final XmlCell xmlCell) {
        return new Cell(xmlCell.getX(), xmlCell.getY());
    }
}
