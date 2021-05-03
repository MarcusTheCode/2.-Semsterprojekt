package presentation;

import domain.CastMember;
import domain.DomainFacade;
import domain.Production;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductionController implements Initializable {

    @FXML
    private Text productionTitle;

    @FXML
    private Button addEntry;

    @FXML
    private Button deleteEntry;

    @FXML
    private Button saveEntry;

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

    public void setAdminToolsVisibility(Boolean bool){
        addEntry.setVisible(bool);
        deleteEntry.setVisible(bool);
        saveEntry.setVisible(bool);
        nameColumn.setEditable(bool);
        roleColumn.setEditable(bool);
    }

    @FXML
    void returnToSearch(MouseEvent event) {
        UIManager.changeScene(UIManager.getSearchScene());
    }

    public void loadProduction(long ID) {
        this.currentProduction = DomainFacade.getProduction(ID);

        productionTitle.setText(currentProduction.getTitle());
        ArrayList<CastMember> castMemberArrayList = currentProduction.getCastMembers();
        castMemberObservableList = FXCollections.observableArrayList(castMemberArrayList);
        castMembers.setItems(castMemberObservableList);
    }

    @FXML
    void addEntry(MouseEvent event) {
        CastMember castMember = new CastMember("\"name\"","\"job\"");
        castMemberObservableList.add(castMember);
        currentProduction.addCastMember(castMember);
    }

    @FXML
    void deleteEntry(MouseEvent event) {
        int index = castMembers.getSelectionModel().getFocusedIndex();
        castMemberObservableList.remove(index);
    }

    @FXML
    void commitJobTitleChange(TableColumn.CellEditEvent<CastMember, String> event) {
        int row = event.getTablePosition().getRow();
        CastMember castMember = ((CastMember) event.getTableView().getItems().get(row));
        castMember.setJobTitle(event.getNewValue());
    }

    @FXML
    void commitNameChange(TableColumn.CellEditEvent<CastMember, String> event) {
        int row = event.getTablePosition().getRow();
        CastMember castMember = ((CastMember) event.getTableView().getItems().get(row));
        castMember.setName(event.getNewValue());
    }

    @FXML
    void saveChanges(MouseEvent event) {
        DomainFacade.editProduction(currentProduction);
    }
}
