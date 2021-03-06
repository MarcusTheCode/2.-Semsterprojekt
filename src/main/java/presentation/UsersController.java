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
import javafx.scene.input.MouseEvent;
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

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        adminColumn.setCellValueFactory(new PropertyValueFactory<>("sysAdmin"));

        usersObservableList = FXCollections.observableArrayList(DomainFacade.getUsers());

        superUsers.setItems(usersObservableList);
    }

    @FXML
    private void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getSearchScene());
        UIManager.getUsersController().loadUser();
    }

    @FXML
    private void addUser(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersInputScene());
        UIManager.getUsersInputController().clearTextField();
        UIManager.getUsersInputController().addUser();
    }

    @FXML
    private void deleteSuperUser(MouseEvent event) throws Exception {
        SuperUser user = superUsers.getSelectionModel().getSelectedItem();
        int index = superUsers.getSelectionModel().getFocusedIndex();

        if (user == null) {
            errorPaneText.setText("No User is selected");
            noUserSelected.setVisible(true);
            throw new Exception("No User is selected");

        } else if (user.getId() == 1) {
            errorPaneText.setText("Can't delete Sysadmin");
            noUserSelected.setVisible(true);
            throw new Exception("Can't delete Sysadmin");

        } else if (user.getId() != DomainFacade.getCurrentUser().getId()) {
            usersObservableList.remove(index);
            DomainFacade.deleteSuperUser(user.getId());
        } else {
            noUserSelected.setVisible(true);
            errorPaneText.setText("Can't delete user");
            throw new Exception("Can't delete user");
        }
    }

    @FXML
    private void editUser(MouseEvent event) throws Exception {
        SuperUser superUser = superUsers.getSelectionModel().getSelectedItem();
        if (superUser == null) {
            errorPaneText.setText("No User is selected");
            noUserSelected.setVisible(true);
            throw new Exception("No user selected");
        } else {
            if (superUsers.getSelectionModel().getSelectedItem().getId() != 1) {
                UIManager.changeScene(UIManager.getUsersInputScene());
                UIManager.getUsersInputController().editUser();
            } else {
                noUserSelected.setVisible(true);
                errorPaneText.setText("You don't have permission to do that");
            }
        }
    }

    @FXML
    private void closeAlertPane(MouseEvent event) {
        noUserSelected.setVisible(false);
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
