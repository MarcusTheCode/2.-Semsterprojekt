package presentation;

import data.DataFacade;
import domain.DomainFacade;
import domain.Production;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private HBox loginHBox;

    @FXML
    private Button usersButton;

    @FXML
    private Button artistsButton;

    @FXML
    private Button loginButton;

    @FXML
    private ComboBox<String> SearchFilterComboBox;

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

    private ObservableList<String> searchOptionsObservableList;

    @FXML
    private AnchorPane noProductionPane;

    @FXML
    private Button alertPaneButton;

    @FXML
    private TextField SearchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        seasonColumn.setCellValueFactory(new PropertyValueFactory<>("seasonNumber"));
        seasonColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        episodeColumn.setCellValueFactory(new PropertyValueFactory<>("episodeNumber"));
        episodeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        searchOptionsObservableList = FXCollections.observableArrayList("Search By Production","Search By Series");
        SearchFilterComboBox.setItems(searchOptionsObservableList);

        loadProductions();
    }

    public void loadProductions() {
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
    void openArtists(MouseEvent event) {
        UIManager.changeScene(UIManager.getArtistsScene());
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
        artistsButton.setVisible(false);
        loginButton.setText("Login");
        DomainFacade.logout();
        UIManager.getSearchController().setAdminToolsVisibility(false);
    }

    public void changeToLoggedIn(){
        if (DomainFacade.getCurrentUser().isSysAdmin()) {
            usersButton.setVisible(true);
            artistsButton.setVisible(true);
        }
        loginButton.setText("Logout");
    }

    @FXML
    void addProduction(MouseEvent event) {
        Production production = new Production(DomainFacade.getCurrentUser().getId());
        productionObservableList.add(production);
        //DomainFacade.saveProduction(production);

        ProductionController productionController = UIManager.getProductionController();
        UIManager.changeScene(UIManager.getProductionScene());
        productionController.setProduction(production);
    }

    @FXML
    void removeProduction(MouseEvent event) throws Exception {
        Production production = productionsTable.getSelectionModel().getSelectedItem();
        if (production == null) {
            noProductionPane.setVisible(true);
            throw new Exception("No production selected");
        }else{
            int index = productionsTable.getSelectionModel().getFocusedIndex();
            productionObservableList.remove(index);

            DomainFacade.deleteProduction(production);
        }
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

        DomainFacade.deleteProduction(production);

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

    // TODO: Consider only searching after typing enter, since this is very power hungry.
    // TODO: searchForProduction method probably needs to be either split up, refactored or documented.
    @FXML
    void searchForProduction(KeyEvent event) {
       if (SearchFilterComboBox.getValue()!=null && SearchBar.getText()!=null) {
           productionObservableList.clear();
           ArrayList<Production> productions = new ArrayList<>(DataFacade.loadAllProductions());
           if (SearchFilterComboBox.getValue().equals("Search By Production")) {
               for (Production production : productions) {
                   if (production.getTitle().toLowerCase().contains(SearchBar.getText().toLowerCase())) {
                       productionObservableList.add(production);
                   }
               }
           }else if (SearchFilterComboBox.getValue().equals("Search By Series")){
               ArrayList<String> SeriesProduction = new ArrayList<>(DataFacade.getSeriesAndProductionID());
               String val[];
               for (String sp: SeriesProduction){
                   val = sp.split(",");
                   if (val[0].toLowerCase().contains(SearchBar.getText().toLowerCase())){
                       productionObservableList.add(DomainFacade.getProduction(Integer.parseInt(val[1])));
                   }
               }
           }
       }
    }

    @FXML
    void SaveChanges(MouseEvent event) {

    }

    public void setAdminToolsVisibility(boolean bool) {
        removeProductionButton.setVisible(bool);
        addProductionButton.setVisible(bool);
        titleColumn.setEditable(bool);
        categoryColumn.setEditable(bool);
    }

}
