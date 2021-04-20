package presentation;

import domain.DomainInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LogInController {
    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private Pane loginFail;

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getStartupScene());
    }



    public void signIn(MouseEvent event) {
        if (DomainInterface.logIn(usernameInput.getText(), passwordInput.getText())) {
            UIManager.changeScene(UIManager.getStartupScene());
            UIManager.getStartupController().setVisibilityLoginButton(false);
        } else {
            loginFail.setVisible(true);
            UIManager.getStartupController().setVisibilityLoginButton(true);
        }
    }

    public void okIncorrectLogin(MouseEvent event) {
        loginFail.setVisible(false);
    }












}
