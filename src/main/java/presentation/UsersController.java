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

public class UsersController implements Initializable {

    private ObservableList<SuperUser> usersObservableList;

    @FXML
    private TableView<SuperUser> superUsers;

    @FXML
    private TableColumn<SuperUser, Integer> idColumn;

    @FXML
    private TableColumn<SuperUser, String> nameColumn;

    @FXML
    private TableColumn<SuperUser, String> passColumn;

    @FXML
    private TableColumn<SuperUser, Boolean> adminColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        //idColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        adminColumn.setCellValueFactory(new PropertyValueFactory<>("sysAdmin"));
        adminColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));

        usersObservableList = FXCollections.observableArrayList(DomainFacade.getUsers());

        superUsers.setItems(usersObservableList);
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getSearchScene());
    }

    @FXML
    void addEntry(MouseEvent event) {
        SuperUser user = DomainFacade.createUser("password1234", "Name", false);
        usersObservableList.add(user);
    }

    @FXML
    void deleteSuperUser(MouseEvent event) {
        int index = superUsers.getSelectionModel().getFocusedIndex();
        int userID = usersObservableList.get(index).getId();

        if (usersObservableList.get(index).getId() == 6){
            return;
        }

        usersObservableList.remove(index);

        DomainFacade.deleteSuperUser(userID);
    }

    @FXML
    void commitNameChange(TableColumn.CellEditEvent<SuperUser, String> event) {
        int row = event.getTablePosition().getRow();
        SuperUser superUser = event.getTableView().getItems().get(row);
        superUser.setUsername(event.getNewValue());

        DomainFacade.editUser(superUser);
    }

    @FXML
    void commitPassChange(TableColumn.CellEditEvent<SuperUser, String> event) {
        int row = event.getTablePosition().getRow();
        SuperUser superUser = event.getTableView().getItems().get(row);
        superUser.setPassword(event.getNewValue());

        DomainFacade.editUser(superUser);
    }

    @FXML
    void commitAdminChange(TableColumn.CellEditEvent<SuperUser, Boolean> event) {
        int row = event.getTablePosition().getRow();
        SuperUser superUser = event.getTableView().getItems().get(row);
        superUser.setSysAdmin(event.getNewValue());

        DomainFacade.editUser(superUser);
    }
}
