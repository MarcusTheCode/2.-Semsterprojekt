package presentation;

import domain.System;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIManager extends Application {

    private static Stage primaryStage;
    private final int WIDTH = 1200;
    private final int HEIGHT = 800;

    private static SceneData loginSceneData;
    private static SceneData productionSceneData;
    private static SceneData startupSceneData;
    private static SceneData searchSceneData;
    private static SceneData usersSceneData;

    public static void launchApplication(){
        launch();
    }

    private SceneData loadScene(String fileName) throws Exception {
        FXMLLoader loader = new FXMLLoader(UIManager.class.getResource(fileName));
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
        Object controller = loader.getController();
        return new SceneData(scene,controller);
    }

    private void loadScenes(){
        try {
            loginSceneData = loadScene("../FXML/Login.fxml");
            productionSceneData = loadScene("../FXML/Production.fxml");
            startupSceneData = loadScene("../FXML/Startup.fxml");
            searchSceneData = loadScene("../FXML/Search.fxml");
            usersSceneData = loadScene("../FXML/Users.fxml");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void changeScene(Scene scene){
        primaryStage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadScenes();

        primaryStage.setScene(startupSceneData.scene);
        primaryStage.setTitle("Credit management system");

        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Scene getLoginScene() {
        return loginSceneData.scene;
    }

    public static Scene getProductionScene() {
        return productionSceneData.scene;
    }

    public static Scene getStartupScene() {
        return startupSceneData.scene;
    }

    public static Scene getSearchScene() {
        return searchSceneData.scene;
    }

    public static Scene getUsersScene() {
        return usersSceneData.scene;
    }

    public static LogInController getLoginController() {
        return (LogInController) loginSceneData.controller;
    }

    public static ProductionController getProductionController() {
        return (ProductionController) productionSceneData.controller;
    }

    public static StartupController getStartupController() {
        return (StartupController) startupSceneData.controller;
    }

    public static SearchController getSearchController() {
        return (SearchController) searchSceneData.controller;
    }

    public static UsersController getUsersController() {
        return (UsersController) usersSceneData.controller;
    }

    private class SceneData {
        public Scene scene;
        public Object controller;

        public SceneData(Scene scene, Object controller) {
            this.scene = scene;
            this.controller = controller;
        }
    }
}
