package presentation;

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
import javafx.scene.input.MouseEvent;
import jdk.jshell.spi.ExecutionControl;

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

        productionObservableList = FXCollections.observableArrayList();

        productionsTable.setItems(productionObservableList);
    }

    public void loadSearchResults(ArrayList<Production> searchResults) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");

        // TODO: Implement communication between SearchController class and DataManger, then uncomment the following lines
        // TODO: Call this function when changing to this scene

        // productionObservableList = FXCollections.observableArrayList(searchResults);
    }

    @FXML
    void goToProduction(MouseEvent event) throws Exception {
        if (productionsTable.getSelectionModel().getSelectedItem() == null)
            throw new Exception("ERROR: No production selected");

        // TODO: somehow pass the selected production to the ProductionController
        UIManager.changeScene(UIManager.getProductionScene());
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getStartupScene());
    }

    @FXML
    void searchForProduction(InputMethodEvent event) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
        // TODO: implement search feature, possibly in DataManger
        // ArrayList<Production> searchResults = DataManager.searchProduction();
        // loadSearchResults(searchResults);
    }
}
