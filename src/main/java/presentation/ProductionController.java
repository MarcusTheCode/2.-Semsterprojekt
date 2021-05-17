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
    private ListView<Genre> genreList;

    @FXML
    private Text productionTitle, saveText;

    @FXML
    private TextField title, type, category, episode, role;

    @FXML
    private Button addEntry, deleteEntry, saveEntry, addGenre, deleteGenre;

    @FXML
    private ComboBox<String> series, season;

    @FXML
    private ComboBox<Genre> genre;

    @FXML
    private ComboBox<Artist> artist;

    private Production currentProduction;

    private ObservableList<CastMember> castMemberObservableList;

    private ObservableList<Genre> genresOberservableList;

    @FXML
    private TableView<CastMember> castMembers;

    @FXML
    private TableColumn<CastMember, String> idColumn, roleColumn, nameColumn, emailColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("artistID"));

        roleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        roleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        castMemberObservableList = FXCollections.observableArrayList();
        genresOberservableList = FXCollections.observableArrayList();

        castMembers.setItems(castMemberObservableList);
        genreList.setItems(genresOberservableList);

        genre.getItems().clear();
        for (Genre g : DomainFacade.getAllGenres()) {
            genre.getItems().add(g);
        }

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

    public void setAdminToolsVisibility(Boolean bool) {
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

        artist.getItems().clear();
        for (Artist a : DomainFacade.getArtists()) {
            artist.getItems().add(a);
        }

        currentProduction = production;

        // Populate general data
        title.setText(production.getTitle());
        type.setText(production.getType());
        category.setText(production.getCategory());
        episode.setText(String.valueOf(production.getEpisodeNumber()));

        // Populate series and season
        Integer seasonID = production.getSeasonID();
        if (seasonID != null) {
            Series s = DomainFacade.getSeriesBySeason(seasonID);
            if (s != null) {
                series.setValue(s.getName());
                season.setValue(String.valueOf(production.getSeasonNumber()));
            }
        }

        productionTitle.setText(currentProduction.getTitle());
        ArrayList<CastMember> castMemberArrayList = currentProduction.getCastMembers();
        castMemberObservableList = FXCollections.observableArrayList(castMemberArrayList);
        castMembers.setItems(castMemberObservableList);

        ArrayList<Genre> genres = currentProduction.getGenres();
        genresOberservableList = FXCollections.observableArrayList(genres);
        genreList.setItems(genresOberservableList);
    }

    @FXML
    void addEntry(MouseEvent event) {
        Artist a = artist.getValue();
        CastMember castMember = new CastMember(a.getName(), a.getEmail(), role.getText(), currentProduction.getId());
        castMemberObservableList.add(castMember);
        currentProduction.addCastMember(castMember);

        DomainFacade.saveCastMember(castMember);
    }

    @FXML
    void deleteEntry(MouseEvent event) {
        int index = castMembers.getSelectionModel().getFocusedIndex();
        CastMember castMember = castMembers.getSelectionModel().getSelectedItem();
        castMemberObservableList.remove(index);
        currentProduction.removeCastMember(castMember);

        DomainFacade.deleteCastMember(castMember);
    }

    @FXML
    void addGenre(MouseEvent event) {
        Genre g = genre.getValue();

        for (Genre gen : currentProduction.getGenres()) {
            if (gen.getId() == g.getId())
                return;
        }

        genresOberservableList.add(g);
        DomainFacade.insertGenre(currentProduction, g);
    }

    @FXML
    void deleteGenre(MouseEvent event) {
        int index = genreList.getSelectionModel().getSelectedIndex();
        Genre g = genreList.getSelectionModel().getSelectedItem();

        genresOberservableList.remove(index);
        DomainFacade.deleteGenre(currentProduction, g);
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
        // Create series, if it doesn't exist
        String seriesValue = series.getValue();
        Series s = DomainFacade.getSeries(seriesValue);
        if (seriesValue != null && !seriesValue.isEmpty() && s == null)
            DomainFacade.createSeries(seriesValue);

        // Create season, if it doesn't exist
        String seasonValue = season.getValue();
        s = DomainFacade.getSeries(seriesValue);
        if (seasonValue != null && !seasonValue.isEmpty() && s != null) {
            Season season = DomainFacade.getSeason(Integer.parseInt(seasonValue), s.getId());
            if (season == null)
                DomainFacade.createSeason(Integer.parseInt(seasonValue), s.getId());
        }

        // TODO: Create genres, if they don't exist

        // Save seasonID
        if (seasonValue != null && !seasonValue.isEmpty() && s != null) {
            Season season = DomainFacade.getSeason(Integer.parseInt(seasonValue), s.getId());
            if (season != null)
                currentProduction.setSeasonID(season.getId());
        }

        // Save general data
        currentProduction.setTitle(title.getText());
        currentProduction.setCategory(category.getText());
        currentProduction.setType(type.getText());
        currentProduction.setEpisodeNumber(Integer.parseInt(episode.getText()));

        // Bit of an ugly hack
        if (currentProduction.getId() == null) {
            DomainFacade.saveProduction(currentProduction);
        } else {
            DomainFacade.editProduction(currentProduction);
        }

        UIManager.getSearchController().loadProductions();

        saveText.setVisible(true);
    }
}
