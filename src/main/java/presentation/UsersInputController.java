package presentation;

import domain.DomainFacade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersInputController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private CheckBox isAdminCheckbox;

    @FXML
    private Button addUserButton, saveChangesButton;

    @FXML
    private AnchorPane errorPane;

    private final String nameRegularExpression = "[a-zA-Z0-9]+";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void returnToUser(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersScene());
        UIManager.getUsersController().loadUser();
    }

    @FXML
    private void saveUser(MouseEvent event) {
        if (username.getText().matches(nameRegularExpression) && password.getText().matches(nameRegularExpression)) {
            createUser(username.getText(), password.getText(), isAdminCheckbox.isSelected());
            UIManager.getUsersController().loadUser();
        }
        else {
            errorPane.setVisible(true);
        }
    }

    @FXML
    private void saveChanges(MouseEvent event) {
        if (username.getText().matches(nameRegularExpression) && password.getText().matches(nameRegularExpression)) {
            UIManager.saveUserChanges(UIManager.getUsersController().getSelectedID(), password.getText(), username.getText(), isAdminCheckbox.isSelected());
            UIManager.changeScene(UIManager.getUsersScene());
            UIManager.getUsersController().loadUser();
        }
        else {
            errorPane.setVisible(true);
        }
    }


    private void createUser(String username, String password, boolean isAdmin) {
        DomainFacade.createUser(password, username, isAdmin);
        UIManager.changeScene(UIManager.getUsersScene());
    }

    public void clearTextField() {
        username.clear();
        password.clear();
        isAdminCheckbox.setSelected(false);
    }

    public void editUser() {
        addUserButton.setVisible(false);
        saveChangesButton.setVisible(true);
        username.setText(UIManager.getUsersController().getUsername());
        password.setText(UIManager.getUsersController().getPassword());
        isAdminCheckbox.setSelected(UIManager.getUsersController().getAdminStatus());
    }

    public void addUser() {
        addUserButton.setVisible(true);
        saveChangesButton.setVisible(false);
    }

    @FXML
    private void closeAlertPane(MouseEvent event) {
        errorPane.setVisible(false);
    }
}
