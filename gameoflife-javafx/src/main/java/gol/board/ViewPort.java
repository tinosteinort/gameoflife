package gol.board;

import gol.Cell;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Tino on 31.01.2016.
 */
public class ViewPort {

    public static final int DEFAULT_CELL_WIDTH = 20;
    private int viewPortX;
    private int viewPortY;

    private final IntegerProperty cellWidthProperty;
    private final DoubleProperty viewPortWidthProperty;
    private final DoubleProperty viewPortHeightProperty;

    public ViewPort(final double viewPortWidth, final double viewPortHeight) {
        this.cellWidthProperty = new SimpleIntegerProperty(DEFAULT_CELL_WIDTH);
        this.viewPortWidthProperty = new SimpleDoubleProperty(viewPortWidth);
        this.viewPortHeightProperty = new SimpleDoubleProperty(viewPortHeight);
    }

    public boolean cellIsInViewPort(final Cell cell) {

        final boolean xIsInBounds = cell.getX() >= viewPortX
                        && cell.getX() < (viewPortX + horizontalCellCount());
        final boolean yIsInBounds = cell.getY() >= viewPortY
                        && cell.getY() < (viewPortY + verticalCellCount());

        return xIsInBounds && yIsInBounds;
    }

    private int horizontalCellCount() {
        return (int) (viewPortWidthProperty.get() / cellWidthProperty.get());
    }
    private int verticalCellCount() {
        return (int) (viewPortHeightProperty.get() / cellWidthProperty.get());
    }

    public double viewPortXinPixel() {
        return viewPortX * cellWidthProperty.get();
    }
    public double viewPortYinPixel() {
        return viewPortY * cellWidthProperty.get();
    }

    public int getViewPortX() {
        return viewPortX;
    }
    public void setViewPortX(int viewPortX) {
        this.viewPortX = viewPortX;
    }

    public int getViewPortY() {
        return viewPortY;
    }
    public void setViewPortY(int viewPortY) {
        this.viewPortY = viewPortY;
    }

    public IntegerProperty cellWidthPropertyProperty() {
        return cellWidthProperty;
    }
    public DoubleProperty viewPortWidthProperty() {
        return viewPortWidthProperty;
    }
    public DoubleProperty viewPortHeightProperty() {
        return viewPortHeightProperty;
    }
}
