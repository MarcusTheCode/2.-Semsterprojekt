package presentation;

import domain.DomainFacade;
import domain.SuperUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ArtistController implements Initializable {

    @FXML
    private TextField name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void returnToArtists(MouseEvent event) {
        UIManager.changeScene(UIManager.getArtistsScene());
    }

    @FXML
    void saveArtist(MouseEvent event) {
        createArtist(name.getText());
    }

    @FXML
    void enterName(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER);
        {
            createArtist(name.getText());
        }
    }

    void createArtist(String artistName) {
        //DomainFacade.createArtist(artistName);
    }
}
