package presentation;

import domain.DomainFacade;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class LoginController {
    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPassword;

    @FXML
    private Text errorMessage;

    @FXML
    private void returnToStartup(MouseEvent event) {
        UIManager.getSearchController().loadProductions();
        UIManager.changeScene(UIManager.getSearchScene());
        clearLoginTextField();
    }

    @FXML
    private void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            loginUser();
        }
    }

    public void login(MouseEvent event) {
        loginUser();
        clearLoginTextField();
    }

    private void loginUser() {
        if (DomainFacade.login(inputUsername.getText(), inputPassword.getText())) {
            UIManager.changeScene(UIManager.getSearchScene());

            UIManager.getSearchController().loadProductions();
            UIManager.getProductionController().setAdminToolsVisibility(true);
            UIManager.getSearchController().setAdminToolsVisibility(true);
            UIManager.getSearchController().changeToLoggedIn();
            UIManager.getArtistsController().enableButtons();
            hideErrorMessage();
        } else {
            errorMessage.setVisible(true);
        }
    }

    public void hideErrorMessage() {
        errorMessage.setVisible(false);
    }

    private void clearLoginTextField() {
        inputUsername.clear();
        inputPassword.clear();
    }

}
