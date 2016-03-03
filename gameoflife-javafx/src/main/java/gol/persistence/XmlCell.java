package gol.persistence;

import javax.xml.bind.annotation.*;

/**
 * Created by Tino on 18.02.2016.
 */
@XmlRootElement(name = "Cell")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlCell {

    @XmlAttribute(name = "x")
    private int x;

    @XmlAttribute(name = "y")
    private int y;

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
