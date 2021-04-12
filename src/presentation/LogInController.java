package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LogInController {
    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getStartupScene());
    }

    @FXML
    void signIn(MouseEvent event) {
        UIManager.changeScene(UIManager.getStartupScene());
    }
}
