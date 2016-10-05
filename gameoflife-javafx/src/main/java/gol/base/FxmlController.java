package gol.base;

import com.github.tinosteinort.beanrepository.BeanRepository;
import com.github.tinosteinort.beanrepository.PostConstructible;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Created by Tino on 14.05.2016.
 */
public abstract class FxmlController implements PostConstructible {

    protected Node view;

    protected abstract String getFxml();

    protected void afterFxmlInitialisation() {

    }

    public Node getView() {
        return view;
    }

    @Override
    public void onPostConstruct(final BeanRepository repository) {
        try {
            view = loadFxml();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        afterFxmlInitialisation();
    }

    protected Node loadFxml() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(this); // Works only, if no Controller is defined in FXML File
        loader.setLocation(getClass().getResource(getFxml()));
        return loader.load();
    }
}
