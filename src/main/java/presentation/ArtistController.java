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

public class ArtistController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField email;

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
        createArtist(name.getText(), email.getText());
    }

    @FXML
    void enterName(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            createArtist(name.getText(), email.getText());
        }
    }

    void createArtist(String artistName, String artistEmail) {
        DomainFacade.createArtist(artistName, artistEmail);
        UIManager.changeScene(UIManager.getArtistsScene());
    }
}
