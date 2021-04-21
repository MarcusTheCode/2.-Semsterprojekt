
package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

public class StartupController {

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<?> searchResult;

    @FXML
    private Button usersButton;

    @FXML
    private Button loginButton;

    @FXML
    void openUsers(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersScene());
    }

    @FXML
    void logIn(MouseEvent event) {
        UIManager.changeScene(UIManager.getLoginScene());
    }

    public void setVisibilityLoginButton(boolean bool) {
        loginButton.setVisible(bool);
        usersButton.setVisible(!bool);
    }

    @FXML
    void searchProductions(MouseEvent event) {
        // TODO: if there is input in the search bar, pass it to the Search scene
        UIManager.changeScene(UIManager.getSearchScene());

        /*
    //Added from Scene builder
    @FXML
    private Button buttonLogout;


    }
    public void cancelLogin() {

        Platform.exit();
    }

    public void logout() throws IOexception {

      //opens login window after logout. Alternatively change to startup window

        buttonLogout.getScene().getWindow().hide();

        Anchorpane root = (AnchorPane)FXMLoader.load(getClass().getResource("../FXML/Login.fxml"));
        Scene scene = new Scene(root,x,y);
        scene.getStyleSheets().add(getClass().getResource("...").toExternalForm());


        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
        */
    }



}
