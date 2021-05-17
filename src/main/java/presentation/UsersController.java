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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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

    @FXML
    private AnchorPane noUserSelected;

    @FXML
    private Text errorPaneText;

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
        UIManager.getUsersController().loadUser();
    }

   /* @FXML
    void addEntry(MouseEvent event) {
        SuperUser user = DomainFacade.createUser("password1234", "Name", false);
        usersObservableList.add(user);
    } */

    @FXML
    void addUser(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersInputScene());
        UIManager.getUsersInputController().clearTextField();
        UIManager.getUsersInputController().addUser();
    }

    @FXML
    void deleteSuperUser(MouseEvent event) throws Exception {
        SuperUser superUser = superUsers.getSelectionModel().getSelectedItem();
        int index = superUsers.getSelectionModel().getFocusedIndex();
        int userID = usersObservableList.get(index).getId();

        if (superUser == null) {
            noUserSelected.setVisible(true);
            throw new Exception("No user selected");}
        else { if (superUsers.getSelectionModel().getSelectedItem().getId() != 1){
        usersObservableList.remove(index);
        DomainFacade.deleteSuperUser(userID);
        }else{
            noUserSelected.setVisible(true);}
            errorPaneText.setText("You don't have permission to do that");}
    }

    @FXML
    void editUser(MouseEvent event) throws Exception {
        SuperUser superUser = superUsers.getSelectionModel().getSelectedItem();
        if (superUser == null) {
            noUserSelected.setVisible(true);
            throw new Exception("No user selected");
        }else{
            if (superUsers.getSelectionModel().getSelectedItem().getId() != 1){
        UIManager.changeScene(UIManager.getUsersInputScene());
        UIManager.getUsersInputController().editUser();}
        else{
            noUserSelected.setVisible(true);
            errorPaneText.setText("You don't have permission to do that");}
        }
    }

    @FXML
    void closeAlertPane(MouseEvent event) {
        noUserSelected.setVisible(false);
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

    public void loadUser() {
        usersObservableList = FXCollections.observableArrayList(DomainFacade.getUsers());

        superUsers.setItems(usersObservableList);
    }

    public String getUsername() {
        return superUsers.getSelectionModel().getSelectedItem().getUsername();
    }

    public String getPassword() {
        return superUsers.getSelectionModel().getSelectedItem().getPassword();
    }

    public boolean getAdminStatus() {
        return superUsers.getSelectionModel().getSelectedItem().isSysAdmin();
    }

    public int getSelectedID() {
        return superUsers.getSelectionModel().getSelectedItem().getId();
    }

}
