package gol.gui;

import com.github.tinosteinort.beanrepository.BeanRepository;
import gol.base.BeanBootstrap;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Tino on 21.01.2016.
 */
public class GameOfLifeApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        final BeanRepository repository = new BeanBootstrap().bootstrap(primaryStage);

        final GameOfLifeGuiController controller = repository.getBean(GameOfLifeGuiController.class);

        final Parent root = (Parent) controller.getView();
        final Scene scene = new Scene(root, 700, 500);

        controller.paint();

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.show();
    }
}
