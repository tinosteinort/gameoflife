package gol.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Tino on 21.01.2016.
 */
public class GameOfLifeGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameOfLifeGui.fxml"));
        final Parent root = loader.load();

        final GameOfLifeGuiController controller = loader.getController();
        controller.initController(new DialogSupport(primaryStage));

        final Scene scene = new Scene(root, 700, 500);

        controller.paint();

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.show();
    }
}
