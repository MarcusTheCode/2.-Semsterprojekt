package presentation;

import domain.DomainInterface;
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
    private TableColumn<SuperUser, Long> idColumn;

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

        usersObservableList = FXCollections.observableArrayList(DomainInterface.getUsers());

        superUsers.setItems(usersObservableList);
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getStartupScene());
    }

    @FXML
    void addEntry(MouseEvent event) {
        SuperUser user = new SuperUser(4, "password1234", "Name", false);
        usersObservableList.add(user);

        DomainInterface.saveUser(user);
    }

    @FXML
    void deleteEntry(MouseEvent event) {
        int index = superUsers.getSelectionModel().getFocusedIndex();
        long id = usersObservableList.get(index).getId();
        usersObservableList.remove(index);

        DomainInterface.deleteUser(id);
    }

    @FXML
    void commitNameChange(TableColumn.CellEditEvent<SuperUser, String> event) {
        int row = event.getTablePosition().getRow();
        SuperUser superUser = event.getTableView().getItems().get(row);
        superUser.setUsername(event.getNewValue());

        DomainInterface.editUser(superUser);
    }

    @FXML
    void commitPassChange(TableColumn.CellEditEvent<SuperUser, String> event) {
        int row = event.getTablePosition().getRow();
        SuperUser superUser = event.getTableView().getItems().get(row);
        superUser.setPassword(event.getNewValue());

        DomainInterface.editUser(superUser);
    }

    @FXML
    void commitAdminChange(TableColumn.CellEditEvent<SuperUser, Boolean> event) {
        int row = event.getTablePosition().getRow();
        SuperUser superUser = event.getTableView().getItems().get(row);
        superUser.setSysAdmin(event.getNewValue());

        DomainInterface.editUser(superUser);
    }
}
