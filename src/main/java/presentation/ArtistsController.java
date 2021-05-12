package presentation;

import domain.CastMember;
import domain.DomainFacade;
import domain.SuperUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ArtistsController implements Initializable {

    private ObservableList<CastMember> artistsObservableList;

    // TODO: Change to Artist instead of SuperUser
    @FXML
    private TableView<CastMember> artists;

    @FXML
    private TableColumn<CastMember, Integer> idColumn;

    @FXML
    private TableColumn<CastMember, String> nameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //artistsObservableList = FXCollections.observableArrayList(DomainFacade.getArtists());

        //superUsers.setItems(artists);
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getSearchScene());
    }

    @FXML
    void addArtist(MouseEvent event) {
        UIManager.changeScene(UIManager.getArtistScene());
    }

    @FXML
    void deleteArtist(MouseEvent event) {
        int index = artists.getSelectionModel().getFocusedIndex();
        int userID = artistsObservableList.get(index).getId();

        if (artistsObservableList.get(index).getId() == 6){
            return;
        }

        artistsObservableList.remove(index);

        DomainFacade.deleteSuperUser(userID);
    }

    @FXML
    void commitNameChange(TableColumn.CellEditEvent<SuperUser, String> event) {
        int row = event.getTablePosition().getRow();
        if (true) return;
        SuperUser superUser = event.getTableView().getItems().get(row);
        superUser.setUsername(event.getNewValue());

        DomainFacade.editUser(superUser);
    }
}
