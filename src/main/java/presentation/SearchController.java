package presentation;

import data.DataFacade;
import domain.DomainFacade;
import domain.Production;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private HBox loginHBox;

    @FXML
    private Button usersButton;

    @FXML
    private Button loginButton;

    @FXML
    private TableView<Production> productionsTable;

    @FXML
    private TableColumn<Production, String> titleColumn;

    @FXML
    private TableColumn<Production, String> categoryColumn;

    @FXML
    private TableColumn<Production, String> typeColumn;

    @FXML
    private TableColumn<Production, Integer> seasonColumn;

    @FXML
    private TableColumn<Production, Integer> episodeColumn;

    @FXML
    private Button addProductionButton;

    @FXML
    private Button removeProductionButton;

    private ObservableList<Production> productionObservableList;

    @FXML
    private AnchorPane noProductionPane;

    @FXML
    private Button alertPaneButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        seasonColumn.setCellValueFactory(new PropertyValueFactory<>("seasonsNumber"));
        seasonColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        episodeColumn.setCellValueFactory(new PropertyValueFactory<>("episodeNumber"));
        episodeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        loadProductions();
    }

    public void loadProductions(){
        ArrayList<Production> productions = DataFacade.loadAllProductions();

        if (DomainFacade.getCurrentUser() != null && !DomainFacade.getCurrentUser().isSysAdmin()) {
            // remove productions from the list if they don't belong to the current user
            productions.removeIf(production -> !production.isOwner(DomainFacade.getCurrentUser()));
        }

        productionObservableList = FXCollections.observableList(productions);
        productionsTable.setItems(productionObservableList);
    }

    @FXML
    void openUsers(MouseEvent event) {
        UIManager.changeScene(UIManager.getUsersScene());
    }

    @FXML
    void toggleLogin(MouseEvent event) {
        if (DomainFacade.getCurrentUser() != null){
            changeToLoggedOut();
            UIManager.getProductionController().setAdminToolsVisibility(false);
        }

        goToLogin();
    }

    private void goToLogin(){
        UIManager.changeScene(UIManager.getLoginScene());
    }

    public void changeToLoggedOut(){
        usersButton.setVisible(false);
        loginButton.setText("Login");
        DomainFacade.logout();
        UIManager.getSearchController().setAdminToolsVisibility(false);
    }

    public void changeToLoggedIn(){
        if (DomainFacade.getCurrentUser().isSysAdmin()) {
            usersButton.setVisible(true);
        }
        loginButton.setText("Logout");
    }

    @FXML
    void addProduction(MouseEvent event) {
        Production production = new Production(DomainFacade.getCurrentUser().getId());
        productionObservableList.add(production);
        DomainFacade.saveProduction(production);
    }

    @FXML
    void removeProduction(MouseEvent event) {
        int index = productionsTable.getSelectionModel().getFocusedIndex();
        Production production = productionObservableList.get(index);
        productionObservableList.remove(index);

        DomainFacade.removeProduction(production);
    }

    @FXML
    void commitCategoryChange(TableColumn.CellEditEvent<Production, String> event) {
        int row = event.getTablePosition().getRow();
        Production production = event.getTableView().getItems().get(row);
        production.setCategory(event.getNewValue());

        DomainFacade.editProduction(production);
    }

    @FXML
    void commitIDchange(TableColumn.CellEditEvent<Production, Integer> event) {
        int row = event.getTablePosition().getRow();
        Production production = event.getTableView().getItems().get(row);

        DomainFacade.removeProduction(production);

        production.setID(event.getNewValue());

        DomainFacade.saveProduction(production);
    }

    @FXML
    void commitOwnerIDchange(TableColumn.CellEditEvent<Production, Integer> event) {
        int row = event.getTablePosition().getRow();
        Production production = event.getTableView().getItems().get(row);
        production.setOwnerID(event.getNewValue());

        DomainFacade.editProduction(production);
    }

    @FXML
    void commitTitleChange(TableColumn.CellEditEvent<Production, String> event) {
        int row = event.getTablePosition().getRow();
        Production production = event.getTableView().getItems().get(row);
        production.setTitle(event.getNewValue());

        DomainFacade.editProduction(production);
    }

    @FXML
    void goToProduction(MouseEvent event) throws Exception {
        Production production = productionsTable.getSelectionModel().getSelectedItem();
        ProductionController productionController = UIManager.getProductionController();
        if (production == null) {
            noProductionPane.setVisible(true);
            throw new Exception("No production selected");
        }else{
            UIManager.changeScene(UIManager.getProductionScene());
            productionController.loadProduction(production.getId());
        }
    }

    //Closing the pane, that open when you attempt to show production without highligthing any.
    @FXML
    void closeAlertPane(MouseEvent event) {
        noProductionPane.setVisible(false);
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getSearchScene());
    }

    @FXML
    void searchForProduction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            loadProductions();
        }
    }

    public void setAdminToolsVisibility(boolean bool) {
        removeProductionButton.setVisible(bool);
        addProductionButton.setVisible(bool);
        titleColumn.setEditable(bool);
        categoryColumn.setEditable(bool);
    }

}
