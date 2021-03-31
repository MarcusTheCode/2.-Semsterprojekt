package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class UIController {


    //Login

    @FXML
    private TextField usernameInput, passwordInput;

    @FXML
    private Button signInButton, returnToStartButton;

    //Startup

    @FXML
    private TextField startUpSearchBar;

    @FXML
    private Button startUpLogin;

    @FXML
    private ListView<?> startUpListView;

}
