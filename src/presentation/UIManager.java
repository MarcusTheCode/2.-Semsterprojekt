package presentation;

import domain.System;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIManager extends Application {

    private static Stage primaryStage;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;

    private static Scene loginScene;
    private static Scene productionScene;
    private static Scene startupScene;

    public static void main(String[] args) {
        launch();
    }

    private Scene loadScene(String fileName) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fileName));
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        // If we need to save the controllers, uncomment the line below
        // Controller controller = loader.getController();
        return scene;
    }

    private void loadScenes(){
        try {
            loginScene = loadScene("../FXML/Login.fxml");
            productionScene = loadScene("../FXML/Production.fxml");
            startupScene = loadScene("../FXML/Startup.fxml");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void changeScene(Scene scene){
        primaryStage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System system = new System(this);

        loadScenes();

        primaryStage.setScene(startupScene);
        primaryStage.setTitle("Credit management system");

        this.primaryStage = primaryStage;
        primaryStage.show();
    }

    public static Scene getLoginScene() {
        return loginScene;
    }

    public static Scene getProductionScene() {
        return productionScene;
    }

    public static Scene getStartupScene() {
        return startupScene;
    }
}
