package gol.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Tino on 21.01.2016.
 */
public class GameOfLifeGui extends Application {

    @FXML
    private Canvas canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameOfLifeGui.fxml"));
        final Parent root = loader.load();

        final Scene scene = new Scene(root, 700, 500);

        final GameOfLifeGuiController controller = loader.getController();
        controller.initController(scene);

        controller.paint();

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.show();

    }
}
