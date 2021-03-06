package presentation;

import domain.DomainFacade;
import domain.Production;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private ComboBox<String> searchFilterComboBox;

    @FXML
    private TableView<Production> productionsTable;

    @FXML
    private TableColumn<Production, String> titleColumn, categoryColumn, typeColumn;

    @FXML
    private TableColumn<Production, Integer> seasonColumn, episodeColumn;

    @FXML
    private Button addProductionButton, removeProductionButton, usersButton, loginButton;

    private ObservableList<Production> productionObservableList;

    private ObservableList<String> searchOptionsObservableList;

    @FXML
    private AnchorPane noProductionPane;

    @FXML
    private Text currentLoginText;

    @FXML
    private TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        seasonColumn.setCellValueFactory(new PropertyValueFactory<>("seasonNumber"));

        episodeColumn.setCellValueFactory(new PropertyValueFactory<>("episodeNumber"));

        searchOptionsObservableList = FXCollections.observableArrayList("Search By Production", "Search By Series");
        searchFilterComboBox.setItems(searchOptionsObservableList);
        searchFilterComboBox.setValue(searchOptionsObservableList.get(0));

        loadProductions();
    }

    public void loadProductions() {
        ArrayList<Production> productions = DomainFacade.getAllProductions();

        if (DomainFacade.getCurrentUser() != null && !DomainFacade.getCurrentUser().isSysAdmin()) {
            // remove productions from the list if they don't belong to the current user
            productions.removeIf(production -> !production.isOwner(DomainFacade.getCurrentUser()));
        }

        productionObservableList = FXCollections.observableList(productions);
        productionsTable.setItems(productionObservableList);
    }

    @FXML
    private void openUsers(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersScene());
    }

    @FXML
    private void openArtists(MouseEvent event) {
        UIManager.changeScene(UIManager.getArtistsScene());
    }

    @FXML
    private void toggleLogin(MouseEvent event) {
        if (DomainFacade.getCurrentUser() != null){
            changeToLoggedOut();
            UIManager.getProductionController().setAdminToolsVisibility(false);
        }
        goToLoginScene();
    }

    private void goToLoginScene(){
        UIManager.changeScene(UIManager.getLoginScene());
    }

    public void changeToLoggedOut(){
        usersButton.setVisible(false);
        currentLoginText.setVisible(false);
        loginButton.setText("Login");
        DomainFacade.logout();
        UIManager.getSearchController().setAdminToolsVisibility(false);
        UIManager.getArtistsController().disableButtons();
    }

    public void changeToLoggedIn() {
        String user = DomainFacade.getCurrentUser().getUsername();
        if (DomainFacade.getCurrentUser().isSysAdmin()) {
            usersButton.setVisible(true);
        }
        loginButton.setText("Logout");
        currentLoginText.setVisible(true);
        currentLoginText.setText("Current login: " + user);
    }

    @FXML
    private void addProduction(MouseEvent event) {
        Production production = new Production(DomainFacade.getCurrentUser().getId());
        productionObservableList.add(production);

        ProductionController productionController = UIManager.getProductionController();
        UIManager.changeScene(UIManager.getProductionScene());
        productionController.setProduction(production);
    }

    @FXML
    private void removeProduction(MouseEvent event) throws Exception {
        Production production = productionsTable.getSelectionModel().getSelectedItem();
        if (production == null) {
            noProductionPane.setVisible(true);
            throw new Exception("No production selected");
        } else {
            int index = productionsTable.getSelectionModel().getFocusedIndex();
            productionObservableList.remove(index);

            DomainFacade.deleteProduction(production);
        }
    }

    @FXML
    private void goToProduction(MouseEvent event) throws Exception {
        Production production = productionsTable.getSelectionModel().getSelectedItem();
        ProductionController productionController = UIManager.getProductionController();
        if (production == null) {
            noProductionPane.setVisible(true);
            throw new Exception("No production selected");
        } else {
            UIManager.changeScene(UIManager.getProductionScene());
            productionController.loadProduction(production.getId());
        }
    }

    @FXML
    private void closeAlertPane(MouseEvent event) {
        noProductionPane.setVisible(false);
    }

    @FXML
    private void searchForProduction(KeyEvent event) {
        //if (event.getCode() == KeyCode.ENTER) {
            if (searchFilterComboBox.getValue() != null && searchBar.getText() != null) {
                productionObservableList.clear();
                if (searchFilterComboBox.getValue().equals("Search By Production")) {
                    productionObservableList.setAll(DomainFacade.getProductionsByTitle(searchBar.getText()));
                } else if (searchFilterComboBox.getValue().equals("Search By Series")) {
                    productionObservableList.setAll(DomainFacade.getProductionsBySeries(searchBar.getText()));
                }
            }
        //}
    }

    public void setAdminToolsVisibility(boolean bool) {
        removeProductionButton.setVisible(bool);
        addProductionButton.setVisible(bool);
    }

}
