package presentation;

import domain.System;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UIManager extends Application {

    private Stage primaryStage;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;

    public static void main(String[] args) {
        launch();
    }

    private Scene loadScene(String fileName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
        // If we need to save the controllers, uncomment the line below
        // Controller controller = loader.getController();
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System system = new System(this);
        primaryStage.setScene(loadScene("../FXML/Startup.fxml"));
        primaryStage.setTitle("Credit management system");
        this.primaryStage = primaryStage;
        primaryStage.show();
    }
}
