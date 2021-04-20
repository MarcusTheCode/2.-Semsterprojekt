package presentation;

import domain.DomainInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
        UIManager.changeScene(UIManager.getStartupScene());
    }



    public void login(MouseEvent event) {
        if (DomainInterface.login(inputUsername.getText(), inputPassword.getText())) {
            UIManager.changeScene(UIManager.getStartupScene());
            UIManager.getStartupController().setVisibilityLoginButton(false);
        } else {
            loginFail.setVisible(true);
            UIManager.getStartupController().setVisibilityLoginButton(true);
        }
    }

    public void hideErrorWindow(MouseEvent event) {
        loginFail.setVisible(false);
    }













}
