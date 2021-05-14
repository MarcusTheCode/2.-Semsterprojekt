package presentation;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductionController implements Initializable {

    @FXML
    private ListView<String> genreList;

    @FXML
    private Text productionTitle, saveText;

    @FXML
    private TextField title, type, category, episode;

    @FXML
    private Button addEntry, deleteEntry, saveEntry;

    @FXML
    private ComboBox<String> series, season;

    private Production currentProduction;

    private ObservableList<CastMember> castMemberObservableList;

    private ObservableList<String> genresOberservableList;

    @FXML
    private TableView<CastMember> castMembers;

    @FXML
    private TableColumn<CastMember, String> roleColumn, nameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        roleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        castMemberObservableList = FXCollections.observableArrayList();
        genresOberservableList = FXCollections.observableArrayList();

        castMembers.setItems(castMemberObservableList);
        genreList.setItems(genresOberservableList);

        series.setOnAction((ActionEvent e) -> {
            season.getItems().clear();

            Series seriesName = DomainFacade.getSeries(series.getValue());

            if (seriesName != null) {
                for (Season s : DomainFacade.getSeasons(seriesName.getId())) {
                    season.getItems().add(String.valueOf(s.getSeasonNumber()));
                }
            }
        });
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
        saveText.setVisible(false);
    }

    public void loadProduction(int ID) {
        Production production = DomainFacade.getProduction(ID);

        setProduction(production);
    }

    public void setProduction(Production production) {
        series.getItems().clear();
        for (Series s : DomainFacade.getAllSeries()) {
            series.getItems().add(s.getName());
        }

        currentProduction = production;

        title.setText(production.getTitle());
        type.setText(production.getType());
        category.setText(production.getCategory());
        episode.setText(String.valueOf(production.getEpisodeNumber()));

        Series s = DomainFacade.getSeriesBySeason(production.getSeasonID());
        if (s != null) {
            series.setValue(s.getName());
            season.setValue(String.valueOf(production.getSeasonNumber()));
        }

        productionTitle.setText(currentProduction.getTitle());
        ArrayList<CastMember> castMemberArrayList = currentProduction.getCastMembers();
        castMemberObservableList = FXCollections.observableArrayList(castMemberArrayList);
        castMembers.setItems(castMemberObservableList);

        ArrayList<String> genres = currentProduction.getGenres();
        genresOberservableList = FXCollections.observableArrayList(genres);
        genreList.setItems(genresOberservableList);
    }

    @FXML
    void addEntry(MouseEvent event) {
        CastMember castMember = new CastMember("\"name\"", "\"email\"","\"job\"",currentProduction.getId());
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
        castMember.getArtist().setName(event.getNewValue());
    }

    @FXML
    void commitEmailChange(TableColumn.CellEditEvent<CastMember, String> event) {
        int row = event.getTablePosition().getRow();
        CastMember castMember = ((CastMember) event.getTableView().getItems().get(row));
        castMember.getArtist().setName(event.getNewValue());
    }

    @FXML
    void saveChanges(MouseEvent event) {
        // Bit of a hack
        if (currentProduction.getId() == null) {
            DomainFacade.saveProduction(currentProduction);
        } else {
            DomainFacade.editProduction(currentProduction);
        }
        saveText.setVisible(true);
    }
}
