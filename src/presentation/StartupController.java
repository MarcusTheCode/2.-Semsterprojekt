
package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

public class StartupController {

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<?> searchResult;

    @FXML
    private Button loginButton;

    @FXML
    void logIn(MouseEvent event) {
        UIManager.changeScene(UIManager.getLoginScene());
    }

    public void visibilityLoginButton() {
        loginButton.setVisible(!UIManager.getLoginStatus());
    }

    @FXML
    void searchProductions(MouseEvent event) {
        // TODO: if there is input in the search bar, pass it to the Search scene
        UIManager.changeScene(UIManager.getSearchScene());
    }



}
