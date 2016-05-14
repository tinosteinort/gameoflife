package gol.base;

import javafx.stage.Stage;
import org.springframework.beans.factory.FactoryBean;

/**
 * Needed to provide a Bean of the primary Stage (created by JavaFX) at Runtime.
 *
 * Created by Tino on 14.05.2016.
 */
public class StageFactoryBean implements FactoryBean {

    private final Stage stage;

    public StageFactoryBean(final Stage stage) {
        this.stage = stage;
    }

    @Override
    public Object getObject() {
        return stage;
    }

    @Override
    public Class<Stage> getObjectType() {
        return Stage.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}