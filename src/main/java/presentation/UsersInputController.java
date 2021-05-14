package presentation;

import domain.DomainFacade;
import domain.SuperUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersInputController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private CheckBox isAdminCheckbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void returnToUser(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersScene());
        UIManager.getUsersController().loadUser();
    }

    @FXML
    void saveUser(MouseEvent event) {
        UIManager.getUsersController().loadUser();
        isAdminCheckbox.isSelected();
        createUser(username.getText(), password.getText(), isAdminCheckbox.isSelected());
        UIManager.getUsersController().loadUser();
    }

    void createUser(String username, String password, boolean isAdmin) {
        DomainFacade.createUser(password, username, isAdmin);
        UIManager.changeScene(UIManager.getUsersScene());
    }

    void clearTextField() {
        username.clear();
        password.clear();
    }



}
