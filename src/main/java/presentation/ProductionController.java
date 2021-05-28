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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.lang.System;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductionController implements Initializable {

    @FXML
    private ListView<Genre> genreList;

    @FXML
    private Text productionTitle, saveText;

    @FXML
    private TextField title, type, episode, role;

    @FXML
    private Button addEntry, deleteEntry, saveEntry, addGenre, deleteGenre;

    @FXML
    private ComboBox<String> series, season, category;

    @FXML
    private ComboBox<Genre> genre;

    @FXML
    private ComboBox<Artist> artist;

    @FXML
    private TableView<CastMember> castMembers;

    @FXML
    private AnchorPane noCastMemberSelected;

    @FXML
    private TableColumn<CastMember, String> idColumn, roleColumn, nameColumn, emailColumn;

    private Production currentProduction;

    private ObservableList<CastMember> castMemberObservableList;

    private ObservableList<Genre> genresObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("artistID"));

        roleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        roleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        castMemberObservableList = FXCollections.observableArrayList();
        genresObservableList = FXCollections.observableArrayList();

        castMembers.setItems(castMemberObservableList);
        genreList.setItems(genresObservableList);

        genre.getItems().clear();
        for (Genre g : DomainFacade.getAllGenres()) {
            genre.getItems().add(g);
        }

        category.getItems().clear();
        for (String c : DomainFacade.getAllCategories()) {
            category.getItems().add(c);
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
        // Metadata
        title.setEditable(bool);
        type.setEditable(bool);
        category.setDisable(!bool);
        series.setDisable(!bool);
        season.setDisable(!bool);
        episode.setEditable(bool);
        saveEntry.setVisible(bool);

        // Genres
        addGenre.setVisible(bool);
        deleteGenre.setVisible(bool);
        genre.setVisible(bool);

        // New CastMember
        artist.setVisible(bool);
        role.setVisible(bool);
        addEntry.setVisible(bool);
        deleteEntry.setVisible(bool);

        // CastMembers
        nameColumn.setEditable(bool);
        roleColumn.setEditable(bool);
        emailColumn.setVisible(bool);
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
        if (!production.getCategory().isEmpty())
            category.setValue(production.getCategory());
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
        genresObservableList = FXCollections.observableArrayList(genres);
        genreList.setItems(genresObservableList);
    }

    @FXML
    void addEntry(MouseEvent event) {
        Artist a = artist.getValue();
        CastMember castMember = new CastMember(a.getName(), a.getEmail(), role.getText(), currentProduction.getId());
        castMemberObservableList.add(castMember);
        currentProduction.addCastMember(castMember);

        DomainFacade.insertCastMember(castMember);
    }

    @FXML
    void deleteEntry(MouseEvent event) throws Exception{
        CastMember castMember = castMembers.getSelectionModel().getSelectedItem();
        int index = castMembers.getSelectionModel().getFocusedIndex();
        if (castMember == null) {
            noCastMemberSelected.setVisible(true);
            throw new Exception("No castMember is highlighted");
        } else {
            castMemberObservableList.remove(index);
            currentProduction.removeCastMember(castMember);
        }


        DomainFacade.deleteCastMember(castMember);
    }

    @FXML
    void addGenre(MouseEvent event) {
        Genre g = genre.getValue();

        for (Genre gen : currentProduction.getGenres()) {
            if (gen.getId() == g.getId())
                return;
        }

        currentProduction.addGenre(g);
        genresObservableList.add(g);
        DomainFacade.insertGenre(currentProduction, g);
    }

    @FXML
    void deleteGenre(MouseEvent event) {
        int index = genreList.getSelectionModel().getSelectedIndex();
        Genre g = genreList.getSelectionModel().getSelectedItem();

        currentProduction.removeGenre(g);
        genresObservableList.remove(index);
        DomainFacade.deleteGenre(currentProduction, g);
    }

    @FXML
    void closeAlertPane(MouseEvent event) {
        noCastMemberSelected.setVisible(false);
    }

    @FXML
    void commitJobTitleChange(TableColumn.CellEditEvent<CastMember, String> event) {
        int row = event.getTablePosition().getRow();
        CastMember castMember = event.getTableView().getItems().get(row);
        castMember.setJobTitle(event.getNewValue());
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

        // Save seasonID
        if (seasonValue != null && !seasonValue.isEmpty() && s != null) {
            Season season = DomainFacade.getSeason(Integer.parseInt(seasonValue), s.getId());
            if (season != null)
                currentProduction.setSeasonID(season.getId());
        } else {
            currentProduction.setSeasonID(null);
        }

        // Save general data
        currentProduction.setTitle(title.getText());
        currentProduction.setCategory(category.getValue());
        currentProduction.setType(type.getText());
        if (!episode.getText().isEmpty())
            currentProduction.setEpisodeNumber(Integer.parseInt(episode.getText()));
        else
            currentProduction.setEpisodeNumber(0);

        // Bit of an ugly hack
        if (currentProduction.getId() == null) {
            DomainFacade.insertProduction(currentProduction);
        } else {
            DomainFacade.updateProduction(currentProduction);
        }

        UIManager.getSearchController().loadProductions();

        productionTitle.setText(currentProduction.getTitle());
        saveText.setVisible(true);
    }
}
