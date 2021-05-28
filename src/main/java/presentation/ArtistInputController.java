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

    private final String nameRegularExpression = "[\\p{L}\s]+";

    private Artist currentArtist;

    @FXML
    private AnchorPane errorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void returnToArtists(MouseEvent event) {
        UIManager.changeScene(UIManager.getArtistsScene());
        UIManager.getArtistsController().loadArtists();
    }

    @FXML
    private void saveChanges(MouseEvent event) {
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
    private void enterName(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
           if (name.getText().matches(nameRegularExpression) && email.getText().matches(emailRegularExpression)) {
                createArtist(name.getText(), email.getText());
            }
        }
    }

    private void createArtist(String artistName, String artistEmail) {
        DomainFacade.createArtist(artistName, artistEmail);
        UIManager.changeScene(UIManager.getArtistsScene());
    }

    public void clearTextField() {
        name.clear();
        email.clear();
    }

    public void editArtist(Artist artist) {
        currentArtist = artist;
        if (artist != null) {
            name.setText(artist.getName());
            email.setText(artist.getEmail());
        }
    }

    @FXML
    private void closeAlertPane(MouseEvent event) {
        errorPane.setVisible(false);
    }
}
