package gol.persistence;

import com.github.tinosteinort.beanrepository.BeanAccessor;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.FileSaveService;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Created by Tino on 18.02.2016.
 */
public class PersistenceService {

    private final FileOpenService fileOpenService;
    private final FileSaveService fileSaveService;
    private final MarshallingService marshallingService;

    public PersistenceService(final BeanAccessor beans) {
        this.fileOpenService = beans.getBean(FileOpenService.class);
        this.fileSaveService = beans.getBean(FileSaveService.class);
        this.marshallingService = beans.getBean(MarshallingService.class);
    }

    public void save(final XmlGameOfLifeState gameState) {

        try {
            final byte[] data = marshallingService.marshall(gameState).getBytes();
            final ByteArrayInputStream bis = new ByteArrayInputStream(data);

            final FileContents contents = fileSaveService.saveFileDialog(null, null, bis, null);
            if (contents == null) {
                // TODO show Info Dialog
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public XmlGameOfLifeState load() {

        try {
            final FileContents contents = fileOpenService.openFileDialog(null, null);
            if (contents == null) {
                // TODO show Info Dialog
                return null;
            }
            return marshallingService.unmarshall(read(contents.getInputStream()));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String read(final InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
