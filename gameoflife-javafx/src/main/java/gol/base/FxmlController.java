package gol.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by Tino on 14.05.2016.
 */
public abstract class FxmlController implements InitializingBean {

    protected Node view;

    protected abstract String getFxml();

    protected void afterFxmlInitialisation() {

    }

    public Node getView() {
        return view;
    }

    // @PostConstruct kann anscheinend nicht genutzt werden, wenn mit ClassPathXmlApplicationContext geabreitet wird
    @Override
    public void afterPropertiesSet() throws IOException {
        view = loadFxml();
        afterFxmlInitialisation();
    }
//    @PostConstruct  private void initializeController() throws IOException {
//        view = loadFxml();
//        afterFxmlInitialisation();
//    }

    protected Node loadFxml() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setController(this); // Works only, if no Controller is defined in FXML File
        loader.setLocation(getClass().getResource(getFxml()));
        return loader.load();
    }
}
