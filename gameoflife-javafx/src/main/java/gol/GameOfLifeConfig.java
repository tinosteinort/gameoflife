package gol;

import gol.gui.DialogSupport;
import gol.gui.GameOfLifeGuiController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tino on 07.05.2016.
 */
@Configuration
public class GameOfLifeConfig {

    @Bean public GameOfLifeGuiController gameOfLifeGuiController() {
        return new GameOfLifeGuiController();
    }

    @Bean public DialogSupport dialogSupport() {
        return new DialogSupport();
    }
}
