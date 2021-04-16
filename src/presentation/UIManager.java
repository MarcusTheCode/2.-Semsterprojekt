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


    private static SceneData loginSceneData;
    private static SceneData productionSceneData;
    private static SceneData startupSceneData;
    private static SceneData searchSceneData;
    private static boolean loginInput;

    public static void main(String[] args) {
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
        primaryStage.show();
    }

    public static Scene getLoginScene() {
        return loginSceneData.scene;
    }

    public static Scene getProductionScene() {
        // TODO: Load production data when changing to this scene
        return productionSceneData.scene;
    }

    public static Scene getStartupScene() {
        return startupSceneData.scene;
    }

    public static Scene getSearchScene() {
        return searchSceneData.scene;
    }

    public static LogInController getLoginController() {
        return (LogInController) loginSceneData.controller;
    }

    public static ProductionController getProductionController() {
        // TODO: Load production data when changing to this scene
        return (ProductionController) productionSceneData.controller;
    }

    public static StartupController getStartupController() {
        return (StartupController) startupSceneData.controller;
    }

    public static SearchController getSearchController() {
        return (SearchController) searchSceneData.controller;
    }

    public static void setLoginStatus(boolean loginInput) { UIManager.loginInput = loginInput; }

    public static boolean getLoginStatus() {return loginInput;}

    private class SceneData {
        public Scene scene;
        public Object controller;

        public SceneData(Scene scene, Object controller) {
            this.scene = scene;
            this.controller = controller;
        }
    }
}
