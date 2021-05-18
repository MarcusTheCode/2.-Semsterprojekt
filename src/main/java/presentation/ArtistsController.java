package presentation;

import domain.Artist;
import domain.DomainFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class ArtistsController implements Initializable {

    private ObservableList<Artist> artistsObservableList;

    @FXML
    private TableView<Artist> artists;

    @FXML
    private TableColumn<Artist, Integer> idColumn;

    @FXML
    private TableColumn<Artist, String> nameColumn;

    @FXML
    private TableColumn<Artist, String> emailColumn;

    @FXML
    private Button addArtistButton;

    @FXML
    private Button deleteArtistButton;

    @FXML
    private Button editArtistButton;

    @FXML
    private AnchorPane noArtistSelected;

    @FXML
    private Text errorPaneText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        disableButtons();

        loadArtists();
        idColumn.setEditable(false);
    }

    public void enableButtons(){
        emailColumn.setVisible(true);
        addArtistButton.setVisible(true);
        deleteArtistButton.setVisible(true);
        editArtistButton.setVisible(true);
    }

    public void disableButtons(){
        emailColumn.setVisible(false);
        addArtistButton.setVisible(false);
        deleteArtistButton.setVisible(false);
        editArtistButton.setVisible(false);
    }

    public void loadArtists() {
        artistsObservableList = FXCollections.observableArrayList(DomainFacade.getArtists());

        artists.setItems(artistsObservableList);
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getSearchScene());
    }

    @FXML
    void addArtist(MouseEvent event) {
        UIManager.changeScene(UIManager.getArtistScene());
        UIManager.getArtistController().clearTextField();
    }

    @FXML
    void deleteArtist(MouseEvent event) {
        int index = artists.getSelectionModel().getFocusedIndex();
        int artistID = artistsObservableList.get(index).getId();
        artistsObservableList.remove(index);
        DomainFacade.deleteArtist(artistID);
    }

    @FXML
    void editArtist(MouseEvent event) {
        Artist artist = artists.getSelectionModel().getSelectedItem();
        if (artist == null) {
            noArtistSelected.setVisible(true);
            errorPaneText.setText("No artist is selected");
        } else {
            UIManager.changeScene(UIManager.getArtistScene());
            UIManager.getArtistController().editArtist();
        }
    }

    @FXML
    void closeAlertPane(MouseEvent event) {
        noArtistSelected.setVisible(false);
    }

    public String getName() {
        return artists.getSelectionModel().getSelectedItem().getName();
    }

    public String getEmail() {
        return artists.getSelectionModel().getSelectedItem().getEmail();
    }

    public int getSelectedID() {
        return artists.getSelectionModel().getSelectedItem().getId();
    }
}
