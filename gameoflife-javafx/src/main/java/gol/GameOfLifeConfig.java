package gol;

import gol.gui.DialogSupport;
import gol.gui.DirectionDialogGuiController;
import gol.gui.GameOfLifeGuiController;
import gol.persistence.ConversionService;
import gol.persistence.PersistenceService;
import gol.persistence.ResourceLoaderService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by Tino on 07.05.2016.
 */
@Configuration
public class GameOfLifeConfig {

    @Bean public GameOfLifeGuiController gameOfLifeGuiController() {
        return new GameOfLifeGuiController();
    }

    @Bean public DirectionDialogGuiController directionDialogGuiController() {
        return new DirectionDialogGuiController();
    }

    @Bean @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DialogSupport dialogSupport() {
        return new DialogSupport();
    }

    @Bean public ConversionService conversionService() {
        return new ConversionService();
    }

    @Bean public PersistenceService persistenceService() {
        return new PersistenceService();
    }

    @Bean public ResourceLoaderService resourceLoaderService() {
        return new ResourceLoaderService();
    }
}
