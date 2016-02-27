package gol.gui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;

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

    private Dialog<BoardBounds> boundsDialog;

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

    private Dialog<BoardBounds> getBoundsDialog() {
        if (boundsDialog == null) {
            boundsDialog = new Dialog();
            boundsDialog.setTitle("Game of Live");
            boundsDialog.setHeaderText("Define the Width and Height of the Board.");

            final Label wLabel = new Label("Width:");
            final Spinner<Integer> wSpinner = new Spinner<>();
            wSpinner.setEditable(true);
            wSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 1000, 100));

            final Label hLabel = new Label("Height:");
            final Spinner<Integer> hSpinner = new Spinner<>();
            hSpinner.setEditable(true);
            hSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 1000, 50));

            final GridPane grid = new GridPane();
            grid.add(wLabel, 0, 0);
            grid.add(wSpinner, 1, 0);
            grid.add(hLabel, 0, 1);
            grid.add(hSpinner, 1, 1);
            boundsDialog.getDialogPane().setContent(grid);

            final ButtonType okButtonType = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
            final ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            boundsDialog.getDialogPane().getButtonTypes().addAll(cancelButtonType, okButtonType);

            boundsDialog.setResultConverter(new Callback<ButtonType, BoardBounds>() {
                @Override
                public BoardBounds call(final ButtonType param) {
                    if (param == okButtonType) {
                        final int width = wSpinner.getValue();
                        final int height = hSpinner.getValue();
                        return new BoardBounds(width, height);
                    }
                    return null;
                }
            });
        }
        return boundsDialog;
    }

    public Optional<BoardBounds> getBoundsFromUser() {
        return getBoundsDialog().showAndWait();
    }

    public boolean askUserForDiscardBoard() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game of Life");
        alert.setHeaderText("There are living Cells on the Board.");
        alert.setContentText("Discard current Board?");

        final Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
