package presentation;

import domain.Artist;
import domain.DomainFacade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ArtistInputController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private Button saveChangesButton;

    private final String emailRegularExpression = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";

    private final String nameRegularExpression = "[a-zA-Z]+";

    private Artist currentArtist;

    @FXML
    private AnchorPane errorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void returnToArtists(MouseEvent event) {
        UIManager.changeScene(UIManager.getArtistsScene());
        UIManager.getArtistsController().loadArtists();
    }

    @FXML
    void saveChanges(MouseEvent event) {
        if (name.getText().matches(nameRegularExpression) && email.getText().matches(emailRegularExpression)) {
            if (currentArtist != null) {
                currentArtist.setName(name.getText());
                currentArtist.setEmail(email.getText());
                DomainFacade.saveArtistChanges(currentArtist);
            } else {
                createArtist(name.getText(), email.getText());
            }
            UIManager.changeScene(UIManager.getArtistsScene());
            UIManager.getArtistsController().loadArtists();
        } else {
            errorPane.setVisible(true);
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

    void editArtist(Artist artist) {
        currentArtist = artist;
        if (artist != null) {
            name.setText(artist.getName());
            email.setText(artist.getEmail());
        }
    }

    @FXML
    void closeAlertPane(MouseEvent event) {
        errorPane.setVisible(false);
    }
}
