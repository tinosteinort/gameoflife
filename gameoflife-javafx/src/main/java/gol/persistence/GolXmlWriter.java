package gol.persistence;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.Writer;

/**
 * Created by Tino on 08.03.2017.
 */
public class GolXmlWriter {

    private final XMLStreamWriter writer;

    public GolXmlWriter(final Writer writer) throws XMLStreamException {
        this.writer = XMLOutputFactory.newFactory().createXMLStreamWriter(writer);
    }

    public void writeDocument(final GolXmlCode code) throws XMLStreamException {
        writer.writeStartDocument();
        code.write();
        writer.writeEndDocument();
    }

    public void writeElement(final String element, final GolXmlCode code) throws XMLStreamException {
        writer.writeStartElement(element);
        code.write();
        writer.writeEndElement();
    }

    public void writeText(final String text) throws XMLStreamException {
        writer.writeCharacters(text);
    }

    public void writeAttribute(final String attribute, final String value) throws XMLStreamException {
        writer.writeAttribute(attribute, value);
    }
}

