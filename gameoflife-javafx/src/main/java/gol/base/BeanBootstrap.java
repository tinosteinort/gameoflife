package gol.base;

import com.github.tinosteinort.beanrepository.BeanRepository;
import gol.gui.DialogSupport;
import gol.gui.DirectionDialogGuiController;
import gol.gui.GameOfLifeGuiController;
import gol.persistence.ConversionService;
import gol.persistence.PersistenceService;
import gol.persistence.ResourceLoaderService;
import javafx.stage.Stage;

/**
 * Created by Tino on 14.05.2016.
 */
public class BeanBootstrap {

    public BeanRepository bootstrap(final Stage stage) {

        final BeanRepository repository = new BeanRepository.BeanRepositoryBuilder()
                .singleton(GameOfLifeGuiController.class, GameOfLifeGuiController::new)
                .singleton(DirectionDialogGuiController.class, DirectionDialogGuiController::new)
                .prototype(DialogSupport.class, DialogSupport::new)
                .singleton(ConversionService.class, ConversionService::new)
                .singleton(PersistenceService.class, PersistenceService::new)
                .singleton(ResourceLoaderService.class, ResourceLoaderService::new)
                .instance(stage)
                .build();

        return repository;
    }
}
