package presentation;

import domain.DomainFacade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ArtistInputController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    private final String emailRegularExpression = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";

    private final String nameRegularExpression = "[a-zA-Z]+";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void returnToArtists(MouseEvent event) {
        UIManager.changeScene(UIManager.getArtistsScene());
        UIManager.getArtistsController().loadArtists();
    }

    @FXML
    void saveArtist(MouseEvent event) {
        if (name.getText().matches(nameRegularExpression) && email.getText().matches(emailRegularExpression)) {
            createArtist(name.getText(), email.getText());
            UIManager.getArtistsController().loadArtists();
        }
    }

    @FXML
    void enterName(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
           if (name.getText().matches(nameRegularExpression) && email.getText().matches(emailRegularExpression)) {
                createArtist(name.getText(), email.getText());
            }
        }
    }

    void createArtist(String artistName, String artistEmail) {
        DomainFacade.createArtist(artistName, artistEmail);
        UIManager.changeScene(UIManager.getArtistsScene());
    }

    void clearTextField() {
        name.clear();
        email.clear();
    }
}
