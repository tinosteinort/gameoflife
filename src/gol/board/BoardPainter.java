package gol.board;

import gol.Cell;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

/**
 * Created by Tino on 28.01.2016.
 */
public interface BoardPainter {

    void paint(GraphicsContext gc);

    IntegerProperty cellWithProperty();

    Point2D getPosOnBoard(double mouseX, double mouseY);
    Optional<Cell> getCellAt(Point2D pointOnBoard);

    void setViewPortX(int x);
    void setViewPortY(int y);
    int getViewPortX();
    int getViewPortY();
}
