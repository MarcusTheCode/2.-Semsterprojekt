package presentation;

import domain.DomainFacade;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LogInController {
    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPassword;

    @FXML
    private Pane loginFail;

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getSearchScene());
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
        if (DomainFacade.login(inputUsername.getText(), inputPassword.getText())) {
            UIManager.changeScene(UIManager.getSearchScene());

            UIManager.getProductionController().setAdminToolsVisibility(true);
            UIManager.getSearchController().setAdminToolsVisibility(true);
            UIManager.getSearchController().changeToLoggedIn();
        } else {
            loginFail.setVisible(true);
        }
    }

    public void hideErrorWindow(MouseEvent event) {
        loginFail.setVisible(false);
    }
}
