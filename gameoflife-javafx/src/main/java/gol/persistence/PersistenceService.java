package gol.persistence;

import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Tino on 18.02.2016.
 */
@Component
public class PersistenceService {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public PersistenceService() {
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

    public void save(final Path file, final XmlGameOfLifeState gameState) {

        try (OutputStream os = Files.newOutputStream(file)) {

            marshaller.marshal(gameState, os);
        }
        catch (JAXBException | IOException ex) {
            throw new RuntimeException("Error while writing File", ex);
        }
    }

    public XmlGameOfLifeState load(final Path file) {

        try (InputStream is = Files.newInputStream(file)) {

            return (XmlGameOfLifeState) unmarshaller.unmarshal(is);
        }
        catch (IOException | JAXBException ex) {
            throw new RuntimeException("Error while reading File", ex);
        }
    }
}
