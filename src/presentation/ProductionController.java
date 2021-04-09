package presentation;

import domain.CastMember;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ListPropertyBase;
import javafx.beans.value.ObservableListValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ProductionController {

    @FXML
    private Text productionTitle;

    @FXML
    private TextArea metaData;

    @FXML
    private TableView<CastMember> castMembers;

    @FXML
    void deleteProduction(MouseEvent event) {

    }

    @FXML
    void saveChanges(MouseEvent event) {

        castMembers.getItems().add(new CastMember("Yeet", "Yeeter^tm"));
        castMembers.getItems().add(new CastMember("Yeet", "Yeeter^tm"));
        castMembers.getItems().add(new CastMember("Yeet", "Yeeter^tm"));
    }

}
