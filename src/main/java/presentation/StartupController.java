
package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import domain.DomainFacade;

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
        if (DomainFacade.getCurrentUser() != null){
            changeToLoggedOut();
            UIManager.getProductionController().setAdminToolsVisibility(false);
        }
        UIManager.changeScene(UIManager.getLoginScene());
    }

    public void changeToLoggedOut(){
        usersButton.setVisible(false);
        loginButton.setText("Login");
        DomainFacade.logout();
        UIManager.getSearchController().setAdminToolsVisibility(false);
    }

    public void changeToLoggedIn(){
        if (DomainFacade.getCurrentUser().isSysAdmin()) {
            usersButton.setVisible(true);
        }
        loginButton.setText("Logout");
    }

    @FXML
    void searchProductions(MouseEvent event) {
        UIManager.getSearchController().loadProductions();
        UIManager.changeScene(UIManager.getSearchScene());
    }


}
