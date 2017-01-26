package gol.persistence;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tino on 18.02.2016.
 */
@XmlRootElement(name = "GameOfLifeState")
@XmlType(propOrder = { "boardType", "boardWidth", "boardHeight", "generation", "cells" })
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlGameOfLifeState {

    @XmlElement(name = "BoardType")
    private XmlBoardType boardType;

    @XmlElement(name = "BoardWidth")
    private int boardWidth;

    @XmlElement(name = "BoardHeight")
    private int boardHeight;

    @XmlElement(name = "Generation")
    private long generation;

    @XmlElementWrapper(name = "Cells")
    @XmlElement(name = "Cell")
    private List<XmlCell> cells = new ArrayList<>();

    public XmlBoardType getBoardType() {
        return boardType;
    }
    public void setBoardType(XmlBoardType boardType) {
        this.boardType = boardType;
    }

    public int getBoardWidth() {
        return boardWidth;
    }
    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }
    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public long getGeneration() {
        return generation;
    }
    public void setGeneration(long generation) {
        this.generation = generation;
    }

    public List<XmlCell> getCells() {
        return cells;
    }
    public void setCells(List<XmlCell> cells) {
        this.cells = cells;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XmlGameOfLifeState that = (XmlGameOfLifeState) o;

        if (boardWidth != that.boardWidth) return false;
        if (boardHeight != that.boardHeight) return false;
        if (generation != that.generation) return false;
        if (boardType != that.boardType) return false;
        return cells != null ? cells.equals(that.cells) : that.cells == null;
    }

    @Override public int hashCode() {
        int result = boardType != null ? boardType.hashCode() : 0;
        result = 31 * result + boardWidth;
        result = 31 * result + boardHeight;
        result = 31 * result + (int) (generation ^ (generation >>> 32));
        result = 31 * result + (cells != null ? cells.hashCode() : 0);
        return result;
    }
}
