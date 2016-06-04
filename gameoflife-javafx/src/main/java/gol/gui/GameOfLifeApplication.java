package gol.gui;

import gol.base.SpringBootstrap;
import gol.base.StageHolder;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tino on 21.01.2016.
 */
public class GameOfLifeApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

//        final AnnotationConfigApplicationContext context = new SpringBootstrap().bootstrap(primaryStage);
        final ClassPathXmlApplicationContext context = new SpringBootstrap().bootstrap(primaryStage);

        final StageHolder holder = context.getBean(StageHolder.class);
        holder.set(primaryStage);
        final GameOfLifeGuiController controller = context.getBean(GameOfLifeGuiController.class);

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
