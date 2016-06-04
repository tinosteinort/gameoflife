package gol.base;

import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * Created by Tino on 01.06.2016.
 */
@Component
public class StageHolder {

    private Stage stage;

    public Stage get() {
        return stage;
    }
    public void set(Stage stage) {
        this.stage = stage;
    }
}
