
package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import domain.DomainInterface;

public class StartupController {

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<?> searchResult;

    @FXML
    private Button usersButton;

    @FXML
    private Button loginButton;

    @FXML
    void openUsers(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersScene());
    }

    @FXML
    void goToLogin(MouseEvent event) {
        if (DomainInterface.getCurrentUser() != null){
            changeToLoggedOut();
            UIManager.getProductionController().setAdminTools(false);
        }
        UIManager.changeScene(UIManager.getLoginScene());
    }

    public void changeToLoggedOut(){
        usersButton.setVisible(false);
        loginButton.setText("Login");
        DomainInterface.logout();
        UIManager.getSearchController().setVisibilitySearchButtons(false);
    }

    public void changeToLoggedIn(){
        usersButton.setVisible(true);
        loginButton.setText("Logout");
    }

    @FXML
    void searchProductions(MouseEvent event) {
        // TODO: if there is input in the search bar, pass it to the Search scene
        UIManager.changeScene(UIManager.getSearchScene());
    }


}
