package presentation;

import data.DataInterface;
import domain.DomainInterface;
import domain.Production;
import domain.SuperUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.LongStringConverter;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private TableColumn<Production, Long> IDColumn;

    @FXML
    private TableColumn<Production, Long> ownerIDColumn;

    @FXML
    private TableColumn<Production, String> titleColumn;

    @FXML
    private TableColumn<Production, String> categoryColumn;

    @FXML
    private TableView<Production> productionsTable;

    @FXML
    private Button removeProductionButton, addProductionButton;

    private ObservableList<Production> productionObservableList;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDColumn.setCellValueFactory(new PropertyValueFactory<Production, Long>("id"));
        IDColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));

        ownerIDColumn.setCellValueFactory(new PropertyValueFactory<Production, Long>("ownerID"));
        ownerIDColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));

        titleColumn.setCellValueFactory(new PropertyValueFactory<Production, String>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        categoryColumn.setCellValueFactory(new PropertyValueFactory<Production, String>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        productionObservableList = FXCollections.observableList(DataInterface.loadAllProductions());
        productionsTable.setItems(productionObservableList);

    }

    @FXML
    void addProduction(MouseEvent event) {
        Production production = new Production(DomainInterface.getCurrentUser().getId());
        productionObservableList.add(production);
        DomainInterface.saveProduction(production);
    }

    @FXML
    void removeProduction(MouseEvent event) {
        int index = productionsTable.getSelectionModel().getFocusedIndex();
        Production production = productionObservableList.get(index);
        productionObservableList.remove(index);

        DomainInterface.removeProduction(production);
    }

    @FXML
    void commitCategoryChange(TableColumn.CellEditEvent<Production, String> event) {
        int row = event.getTablePosition().getRow();
        Production production = event.getTableView().getItems().get(row);
        production.setCategory(event.getNewValue());

        DomainInterface.editProduction(production);
    }

    @FXML
    void commitIDchange(TableColumn.CellEditEvent<Production, Long> event) {
        int row = event.getTablePosition().getRow();
        Production production = event.getTableView().getItems().get(row);
        production.setID(event.getNewValue());

        DomainInterface.editProduction(production);
    }

    @FXML
    void commitOwnerIDchange(TableColumn.CellEditEvent<Production, Long> event) {
        int row = event.getTablePosition().getRow();
        Production production = event.getTableView().getItems().get(row);
        production.setOwnerID(event.getNewValue());

        DomainInterface.editProduction(production);
    }

    @FXML
    void commitTitleChange(TableColumn.CellEditEvent<Production, String> event) {
        int row = event.getTablePosition().getRow();
        Production production = event.getTableView().getItems().get(row);
        production.setTitle(event.getNewValue());

        DomainInterface.editProduction(production);
    }

    @FXML
    void goToProduction(MouseEvent event) throws Exception {
        Production production = productionsTable.getSelectionModel().getSelectedItem();
        ProductionController productionController = UIManager.getProductionController();
        if (production == null) {
            throw new Exception("No production selected");
        }else{
            UIManager.changeScene(UIManager.getProductionScene());
            productionController.loadProduction(production.getId());
        }
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getStartupScene());
    }

    @FXML
    void searchForProduction(KeyEvent event){
        if (event.getCode() == KeyCode.getKeyCode("ENTER"));
        {
            productionObservableList = FXCollections.observableList(DataInterface.loadAllProductions());
            productionsTable.setItems(productionObservableList);
        }
    }

    public void setVisibilitySearchButtons(boolean bool) {
        removeProductionButton.setVisible(bool);
        addProductionButton.setVisible(bool);
    }

}
