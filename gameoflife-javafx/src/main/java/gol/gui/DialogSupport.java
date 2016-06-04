package gol.gui;

import gol.base.injection.BeanRepository;
import gol.persistence.ResourceFigure;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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

    private final Stage window;
    private final BeanRepository repository;

    public DialogSupport(final BeanRepository repository) {
        this.window = repository.get(Stage.class);
        this.repository = repository;
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
            boundsDialog = new Dialog<>();
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
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                        "Discard current Board?",
                                        ButtonType.YES, ButtonType.NO);
        alert.setTitle("Game of Life");
        alert.setHeaderText("There are living Cells on the Board.");

        final Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.YES;
    }

    public Optional<ResourceFigure> getResourceFigureEditedByUser(final ResourceFigure originalFigure) {
        final Dialog editDirectionDialog = new Dialog();
        editDirectionDialog.setTitle("Game of Live");
        editDirectionDialog.setHeaderText("Edit the Direction of the Figure.");

        final DirectionDialogGuiController controller = repository.get(DirectionDialogGuiController.class);
        editDirectionDialog.getDialogPane().setContent(controller.getView());

        controller.setFigure(originalFigure);

        final ButtonType okButtonType = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        final ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        editDirectionDialog.getDialogPane().getButtonTypes().addAll(cancelButtonType, okButtonType);

        editDirectionDialog.setResultConverter(new Callback<ButtonType, ResourceFigure>() {
            @Override
            public ResourceFigure call(final ButtonType param) {
                if (param == okButtonType) {
                    return controller.getCurrentResourceFigure();
                }
                return null;
            }
        });

        return editDirectionDialog.showAndWait();
    }
}
