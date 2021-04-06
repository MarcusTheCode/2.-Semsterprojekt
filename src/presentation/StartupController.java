
package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class StartupController {

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<?> searchResult;

    @FXML
    void logIn(MouseEvent event) {
        UIManager.changeScene(UIManager.getLoginScene());
    }

    @FXML
    void searchProductions(MouseEvent event) {

    }

}