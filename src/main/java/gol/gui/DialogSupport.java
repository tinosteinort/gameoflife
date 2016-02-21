package gol.gui;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by Tino on 19.02.2016.
 */
public class DialogSupport {

    private FileChooser fileChooser;
    private Path selectedFolder;

    final FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Game of Live XML", "*.xml");

    private final Window window;

    public DialogSupport(final Window window) {
        this.window = window;
    }

    private FileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Select Game of Life XML File");
            fileChooser.getExtensionFilters().add(filter);
        }
        if (selectedFolder != null) {
            fileChooser.setInitialDirectory(selectedFolder.toFile());
        }
        return fileChooser;
    }

    public Optional<Path> selectPathForOpen() {
        final File selectedFile = getFileChooser().showOpenDialog(window);
        if (selectedFile == null) {
            return Optional.empty();
        }
        final Path selectedPath = selectedFile.toPath();
        saveParentFolder(selectedPath);
        return Optional.of(selectedPath);
    }

    public Optional<Path> selectPathForSave() {
        final File selectedFile = getFileChooser().showSaveDialog(window);
        if (selectedFile == null) {
            return Optional.empty();
        }
        final Path selectedPath = selectedFile.toPath();
        saveParentFolder(selectedPath);
        return Optional.of(selectedPath);
    }

    private void saveParentFolder(final Path selectedPath) {
        selectedFolder = selectedPath.getParent();
    }
}
