package gol.persistence;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by Tino on 26.01.2017.
 */
public class MarshallingService {

    private static final String GAME_OF_LIFE_STATE = "GameOfLifeState";
    private static final String BOARD_TYPE = "BoardType";
    private static final String BOARD_WIDTH = "BoardWidth";
    private static final String BOARD_HEIGHT = "BoardHeight";
    private static final String GENERATION = "Generation";
    private static final String CELLS = "Cells";
    private static final String CELL = "Cell";
    private static final String X = "x";
    private static final String Y = "y";

    public XmlGameOfLifeState unmarshall(final String data) {
        final XmlGameOfLifeState result = new XmlGameOfLifeState();

        try {
            final XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(new StringReader(data));

            while (reader.hasNext()) {
                switch (reader.next()) {

                    case XMLStreamReader.START_ELEMENT:
                        final String localName = reader.getLocalName();
                        switch (localName) {
                            case BOARD_TYPE:
                                result.setBoardType(readBoardType(reader));
                                break;
                            case BOARD_WIDTH:
                                result.setBoardWidth(readInt(reader));
                                break;
                            case BOARD_HEIGHT:
                                result.setBoardHeight(readInt(reader));
                                break;
                            case GENERATION:
                                result.setGeneration(readLong(reader));
                                break;
                            case CELL:
                                result.getCells().add(readCell(reader));
                                break;
                        }
                        break;
                }
            }

            return result;
        }
        catch (XMLStreamException ex) {
            throw new RuntimeException("Error while parsing XML", ex);
        }
    }

    private XmlBoardType readBoardType(final XMLStreamReader reader) throws XMLStreamException {
        reader.next();
        final String boardType = reader.getText();
        return XmlBoardType.valueOf(boardType);
    }

    private int readInt(final XMLStreamReader reader) throws XMLStreamException {
        reader.next();
        final String number = reader.getText();
        return Integer.valueOf(number);
    }

    private long readLong(final XMLStreamReader reader) throws XMLStreamException {
        reader.next();
        final String number = reader.getText();
        return Long.valueOf(number);
    }

    private XmlCell readCell(final XMLStreamReader reader) throws XMLStreamException {
        final XmlCell cell = new XmlCell();
        final String x = reader.getAttributeValue(0);
        cell.setX(Integer.valueOf(x));
        final String y = reader.getAttributeValue(1);
        cell.setY(Integer.valueOf(y));
        return cell;
    }

    public String marshall(final XmlGameOfLifeState gameState) {

        try {
            final StringWriter sw = new StringWriter();

            final GolXmlWriter xml = new GolXmlWriter(sw);
            xml.writeDocument(() -> {
                xml.writeElement(GAME_OF_LIFE_STATE, () -> {

                    xml.writeElement(BOARD_TYPE, () -> xml.writeText(gameState.getBoardType().name()));
                    xml.writeElement(BOARD_WIDTH, () -> xml.writeText(String.valueOf(gameState.getBoardWidth())));
                    xml.writeElement(BOARD_HEIGHT, () -> xml.writeText(String.valueOf(gameState.getBoardHeight())));
                    xml.writeElement(GENERATION, () -> xml.writeText(String.valueOf(gameState.getGeneration())));

                    xml.writeElement(CELLS, () -> {
                        for (XmlCell xmlCell : gameState.getCells()) {
                            xml.writeElement(CELL, () -> {
                                xml.writeAttribute(X, String.valueOf(xmlCell.getX()));
                                xml.writeAttribute(Y, String.valueOf(xmlCell.getY()));
                            });
                        }
                    });
                });
            });

            return sw.toString();
        }
        catch (XMLStreamException ex) {
            throw new RuntimeException("Error while generating XML", ex);
        }
    }
}
