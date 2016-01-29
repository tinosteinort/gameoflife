package gol.board;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Tino on 28.01.2016.
 */
public interface BoardPainter {

    void paint(GraphicsContext gc);

    IntegerProperty cellWithProperty();

    Point2D getPosOnBoard(double mouseX, double mouseY);
    Point2D getFieldAt(Point2D pointOnBoard);

    void navigateLeft();
    void navigateRight();
    void navigateUp();
    void navigateDown();
}
