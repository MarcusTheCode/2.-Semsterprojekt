
package presentation;

import javafx.application.Platform;
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
    void loginClicked(MouseEvent event) {
        changeButtonText();
        UIManager.changeScene(UIManager.getLoginScene());
    }

    public void setVisibilityLoginButton(boolean bool) {
        loginButton.setVisible(bool);
        usersButton.setVisible(!bool);
    }

    public void changeButtonText(){
        if (loginButton.getText().equals("Login")){
            loginButton.setText("Logout");
        }else{
            loginButton.setText("Login");
            DomainInterface.logout();
        }
    }

    @FXML
    void searchProductions(MouseEvent event) {
        // TODO: if there is input in the search bar, pass it to the Search scene
        UIManager.changeScene(UIManager.getSearchScene());
    }


}
