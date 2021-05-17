package presentation;

import data.DataFacade;
import domain.DomainFacade;
import domain.SuperUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <h1>UIManager</h1>
 * <p>This class acts as the interactive part of the application.</p>
 * <p>It handles loading of FXML scenes and Controllers</p>
 * <p>It contains a nested class, SceneData</p>
 */

public class UIManager extends Application {

    private static Stage primaryStage;
    private final int WIDTH = 1200;
    private final int HEIGHT = 800;

    private static SceneData loginSceneData;
    private static SceneData productionSceneData;
    private static SceneData searchSceneData;
    private static SceneData usersSceneData;
    private static SceneData artistsSceneData;
    private static SceneData artistSceneData;
    private static SceneData usersInputSceneData;

    public static void launchApplication() {
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
            searchSceneData = loadScene("../FXML/Search.fxml");
            usersSceneData = loadScene("../FXML/Users.fxml");
            artistsSceneData = loadScene("../FXML/Artists.fxml");
            artistSceneData = loadScene("../FXML/Artist.fxml");
            usersInputSceneData = loadScene("../FXML/UsersInput.fxml");

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

        //primaryStage.setScene(artistsSceneData.scene);
        primaryStage.setScene(searchSceneData.scene);
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

    public static Scene getSearchScene() {
        return searchSceneData.scene;
    }

    public static Scene getUsersScene() {
        return usersSceneData.scene;
    }

    public static Scene getArtistsScene() {
        return artistsSceneData.scene;
    }

    public static Scene getArtistScene() {
        return artistSceneData.scene;
    }

    public static Scene getUsersInputScene() {return usersInputSceneData.scene; }

    public static LogInController getLoginController() {
        return (LogInController) loginSceneData.controller;
    }

    public static ProductionController getProductionController() { return (ProductionController) productionSceneData.controller; }

    public static SearchController getSearchController() {
        return (SearchController) searchSceneData.controller;
    }

    public static UsersController getUsersController() {
        return (UsersController) usersSceneData.controller;
    }

    public static ArtistsController getArtistsController() {
        return (ArtistsController) artistsSceneData.controller;
    }

    public static ArtistController getArtistController() {
        return (ArtistController) artistSceneData.controller;
    }

    public static UsersInputController getUsersInputController() {return (UsersInputController) usersInputSceneData.controller; }



    private class SceneData {
        public Scene scene;
        public Object controller;

        public SceneData(Scene scene, Object controller) {
            this.scene = scene;
            this.controller = controller;
        }
    }

    public static void saveUserChanges(int ID, String password, String username, boolean sysAdmin) {
        SuperUser user = new SuperUser(ID, password, username, sysAdmin);
        DomainFacade.saveUserChanges(user);
    }

}
