package presentation;

import data.DataInterface;
import domain.DomainInterface;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.security.Key;

public class LogInController {
    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPassword;

    @FXML
    private Pane loginFail;

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getStartupScene());
    }

    @FXML
    void usrPassTekstFieldKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            loginUser();
        }
    }

    public void login(MouseEvent event) {
        loginUser();
    }

    private void loginUser() {
        if (DomainInterface.login(inputUsername.getText(), inputPassword.getText())) {
            UIManager.changeScene(UIManager.getStartupScene());

            UIManager.getProductionController().setAdminToolsVisibility(true);
            UIManager.getSearchController().setAdminToolsVisibility(true);
            UIManager.getStartupController().changeToLoggedIn();
        } else {
            loginFail.setVisible(true);
        }
    }

    public void hideErrorWindow(MouseEvent event) {
        loginFail.setVisible(false);
    }
}
