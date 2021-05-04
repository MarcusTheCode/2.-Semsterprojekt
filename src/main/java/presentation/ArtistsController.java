package presentation;

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
import javafx.util.converter.BooleanStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ArtistsController implements Initializable {

    private ObservableList<SuperUser> usersObservableList;

    @FXML
    private TableView<SuperUser> superUsers;

    @FXML
    private TableColumn<SuperUser, Long> idColumn;

    @FXML
    private TableColumn<SuperUser, String> nameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        usersObservableList = FXCollections.observableArrayList(DomainFacade.getUsers());

        superUsers.setItems(usersObservableList);
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getSearchScene());
    }

    @FXML
    void addArtist(MouseEvent event) {
        if (true) return;
        SuperUser user = DomainFacade.createUser("password1234", "Name", false);
        usersObservableList.add(user);
    }

    @FXML
    void deleteArtist(MouseEvent event) {
        int index = superUsers.getSelectionModel().getFocusedIndex();
        long userID = usersObservableList.get(index).getId();

        if (usersObservableList.get(index).getId() == 6){
            return;
        }

        usersObservableList.remove(index);

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
