package gol.board;

import gol.Cell;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by Tino on 31.01.2016.
 */
public class ViewPort {

    public static final int DEFAULT_CELL_WIDTH = 10;
    private int viewPortX;
    private int viewPortY;

    private double viewPortWidth;
    private double viewPortHeight;

    private final IntegerProperty cellWidthProperty;

    public ViewPort(final double viewPortWidth, final double viewPortHeight) {
        this.viewPortWidth = viewPortWidth;
        this.viewPortHeight = viewPortHeight;
        this.cellWidthProperty = new SimpleIntegerProperty(DEFAULT_CELL_WIDTH);
    }

    public boolean cellIsInViewPort(final Cell cell) {

        final boolean xIsInBounds = cell.getX() >= viewPortX
                        && cell.getX() < (viewPortX + horizontalCellCount());
        final boolean yIsInBounds = cell.getY() >= viewPortY
                        && cell.getY() < (viewPortY + verticalCellCount());

        return xIsInBounds && yIsInBounds;
    }

    private int horizontalCellCount() {
        return (int) (viewPortWidth / cellWidthProperty.get());
    }

    private int verticalCellCount() {
        return (int) (viewPortHeight / cellWidthProperty.get());
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

    public double getViewPortWidth() {
        return viewPortWidth;
    }
    public void setViewPortWidth(int viewPortWidth) {
        this.viewPortWidth = viewPortWidth;
    }

    public double getViewPortHeight() {
        return viewPortHeight;
    }
    public void setViewPortHeight(int viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
    }

    public IntegerProperty cellWidthPropertyProperty() {
        return cellWidthProperty;
    }
}
