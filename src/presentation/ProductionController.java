package presentation;

import domain.CastMember;
import domain.DomainInterface;
import domain.Production;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import jdk.jshell.spi.ExecutionControl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductionController implements Initializable {

    @FXML
    private Text productionTitle;

    @FXML
    private TextArea metaData;

    private Production currentProduction;

    private ObservableList<CastMember> castMemberObservableList;

    @FXML
    private TableView<CastMember> castMembers;

    @FXML
    private TableColumn<CastMember, String> roleColumn;

    @FXML
    private TableColumn<CastMember, String> nameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        roleColumn.setCellValueFactory(new PropertyValueFactory<CastMember, String>("jobTitle"));
        roleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setCellValueFactory(new PropertyValueFactory<CastMember, String>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        castMemberObservableList = FXCollections.observableArrayList();

        castMembers.setItems(castMemberObservableList);
    }

    @FXML
    void returnToStartup(MouseEvent event) {
        UIManager.changeScene(UIManager.getStartupScene());
    }

    public void loadProduction(long ID) {
        // TODO: Call this function when changing to this scene
        // TODO: Hide or disable editing features if visitor is not logged in

        this.currentProduction = DomainInterface.getProduction(ID);
        ArrayList<CastMember> castMemberArrayList = currentProduction.getCastMembers();
        castMemberObservableList = FXCollections.observableArrayList(castMemberArrayList);
        metaData.setText(this.currentProduction.getMetaData());
    }

    @FXML
    void addEntry(MouseEvent event) {
        castMemberObservableList.add(new CastMember("\"name\"","\"job\""));
    }

    @FXML
    void deleteEntry(MouseEvent event) {
        int index = castMembers.getSelectionModel().getFocusedIndex();
        castMemberObservableList.remove(index);
    }

    @FXML
    void commitEntryChange(TableColumn.CellEditEvent<CastMember, String> event) {
        int row = event.getTablePosition().getRow();
        CastMember castMember = ((CastMember) event.getTableView().getItems().get(row));
        castMember.setJobTitle(event.getNewValue());
    }

    @FXML
    void deleteProduction(MouseEvent event) {
        DomainInterface.removeProduction(currentProduction);
    }

    @FXML
    void saveChanges(MouseEvent event) {
        DomainInterface.editProduction(currentProduction);
    }
}
