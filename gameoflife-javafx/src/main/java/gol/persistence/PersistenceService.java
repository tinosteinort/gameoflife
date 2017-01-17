package gol.persistence;

import com.github.tinosteinort.beanrepository.BeanAccessor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.FileSaveService;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Tino on 18.02.2016.
 */
public class PersistenceService {

    private final FileOpenService fileOpenService;
    private final javax.jnlp.FileSaveService fileSaveService;

    public PersistenceService(final BeanAccessor beans) {
        this.fileOpenService = beans.getBean(FileOpenService.class);
        this.fileSaveService = beans.getBean(FileSaveService.class);
    }

    public void save(final XmlGameOfLifeState gameState) {

        try {
            final String content = String.format("%s, %d x %d", gameState.getBoardType(), gameState.getBoardWidth(), gameState.getBoardHeight());
            final byte[] data = content.getBytes();
            final ByteArrayInputStream bis = new ByteArrayInputStream(data);

            final FileContents contents = fileSaveService.saveFileDialog(null, null, bis, null);
            if (contents == null) {
                // file can not be saved
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
                return null;
            }

            final String content = read(contents.getInputStream());
            System.out.println(content);

            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "File Viewer",
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Game of Life");
            alert.setHeaderText("Inhalt der geladenen Datei:");
            alert.setContentText(content);

            final Optional<ButtonType> result = alert.showAndWait();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public static String read(final InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
