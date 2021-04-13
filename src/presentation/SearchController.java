package presentation;

import com.sun.javafx.collections.ObservableListWrapper;
import data.DataInterface;
import domain.CastMember;
import domain.Production;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jdk.jshell.spi.ExecutionControl;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private TableColumn<Production, String> IDColumn;

    @FXML
    private TableColumn<Production, String> titleColumn;

    @FXML
    private TableColumn<Production, String> genreColumn;

    @FXML
    private TableView<Production> productionsTable;

    private ObservableList<Production> productionObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IDColumn.setCellValueFactory(new PropertyValueFactory<Production, String>("id"));
        IDColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        titleColumn.setCellValueFactory(new PropertyValueFactory<Production, String>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        genreColumn.setCellValueFactory(new PropertyValueFactory<Production, String>("category"));
        genreColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        productionObservableList = FXCollections.observableList(DataInterface.loadAllProductions());

        //productionsTable.setItems(productionObservableList);
        productionsTable.getItems().setAll(productionObservableList);

    }

    public void loadSearchResults(ArrayList<Production> searchResults) {
        productionObservableList = FXCollections.observableArrayList(searchResults);
        productionsTable.setItems(productionObservableList);
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
        if(event.getCode().equals(KeyCode.ENTER)){

            System.out.println("i did stuff");
        }
    }
}
