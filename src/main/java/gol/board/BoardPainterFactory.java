package gol.board;

/**
 * Created by Tino on 18.02.2016.
 */
public class BoardPainterFactory {

    public BoardPainter build(final Board board, final double width, final double height) {
        if (board instanceof BoundedBoard) {
            return new BoundedBoardPainter((BoundedBoard) board, width, height);
        }
        else if (board instanceof EndlessBoard) {
            return new EndlessBoardPainter((EndlessBoard) board, width, height);
        }
        throw new IllegalStateException("Board not available");
    }
}
