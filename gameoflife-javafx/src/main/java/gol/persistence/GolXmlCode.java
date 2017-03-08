package gol.persistence;

import javax.xml.stream.XMLStreamException;

/**
 * Created by Tino on 08.03.2017.
 */
@FunctionalInterface
interface GolXmlCode {

    void write() throws XMLStreamException;
}
