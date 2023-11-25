package gol.persistence;

import jakarta.xml.bind.annotation.*;
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
}
