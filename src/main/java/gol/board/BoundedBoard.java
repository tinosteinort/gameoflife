package gol.board;

/**
 * Created by Tino on 17.01.2016.
 */
public abstract class BoundedBoard extends Board {

    private final int width;
    private final int height;

    public BoundedBoard(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
