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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            disableButtons();

        loadArtists();
    }

    public void enableButtons(){
        emailColumn.setVisible(true);
        addArtistButton.setVisible(true);
        deleteArtistButton.setVisible(true);
    }

    public void disableButtons(){
        emailColumn.setVisible(false);
        addArtistButton.setVisible(false);
        deleteArtistButton.setVisible(false);
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
    void commitNameChange(TableColumn.CellEditEvent<Artist, String> event) {
        int row = event.getTablePosition().getRow();
        if (true) return;
        Artist artist = event.getTableView().getItems().get(row);
        artist.setName(event.getNewValue());

        //DomainFacade.editArtist(artist);
    }

    @FXML
    void commitEmailChange(TableColumn.CellEditEvent<Artist, String> event) {
        int row = event.getTablePosition().getRow();
        if (true) return;
        Artist artist = event.getTableView().getItems().get(row);
        artist.setName(event.getNewValue());

        //DomainFacade.editArtist(artist);
    }
}
