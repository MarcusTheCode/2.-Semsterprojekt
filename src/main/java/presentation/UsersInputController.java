package presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersInputController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void returnToUser(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersScene());
        UIManager.getUsersController().loadUser();
    }

    

    void clearTextField() {
        username.clear();
        password.clear();
    }



}
