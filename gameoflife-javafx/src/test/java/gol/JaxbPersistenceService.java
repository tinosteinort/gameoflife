package gol;

import gol.persistence.XmlGameOfLifeState;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tino on 18.02.2016.
 */
public class JaxbPersistenceService {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public JaxbPersistenceService() {
        try {
            final JAXBContext context = JAXBContext.newInstance(XmlGameOfLifeState.class);
            this.marshaller = context.createMarshaller();
            this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            this.unmarshaller = context.createUnmarshaller();
        }
        catch (JAXBException ex) {
            throw new RuntimeException("Could not create PersistenceService", ex);
        }
    }

    public String asString(final XmlGameOfLifeState gameState) {

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            marshaller.marshal(gameState, os);
            return os.toString();
        }
        catch (JAXBException ex) {
            throw new RuntimeException("Error while writing File", ex);
        }
        catch (IOException ex) {
            throw new RuntimeException("Error while writing File", ex);
        }
    }

    public XmlGameOfLifeState fromString(final String value) {

        try (InputStream is = new ByteArrayInputStream(value.getBytes())) {

            return (XmlGameOfLifeState) unmarshaller.unmarshal(is);
        }
        catch (IOException ex) {
            throw new RuntimeException("Error while reading File", ex);
        }
        catch (JAXBException ex) {
            throw new RuntimeException("Error while reading File", ex);
        }
    }
}
