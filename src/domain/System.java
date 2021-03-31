package domain;

import javafx.application.Application;
import javafx.stage.Stage;
import jdk.jshell.spi.ExecutionControl;


public class System extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Startup.fxml"));
        primaryStage.setTitle("credz");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO: implement Controller and uncomment line below
        // primaryStage.setScene();
        primaryStage.show();
    }

    public Production getProduction(long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement getProduction
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean addProduction(Production production) throws ExecutionControl.NotImplementedException {
        // TODO: implement addProduction
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean editProduction(Production production) throws ExecutionControl.NotImplementedException {
        // TODO: implement editProduction
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean removeProduction(Production production) throws ExecutionControl.NotImplementedException {
        // TODO: implement removeProduction
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean logIn(String username, String password) throws ExecutionControl.NotImplementedException {
        // TODO: implement logIn
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean logOut() throws ExecutionControl.NotImplementedException {
        // TODO: implement logOut
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean saveSuperUser(SuperUser superUser) throws ExecutionControl.NotImplementedException {
        // TODO: implement saveSuperUser
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean removeSuperUser(long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement removeSuperUser
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    private boolean isSysAdmin(long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement isSysAdmin
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    private boolean isProductionIDValid(long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement isProductionIDValid
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    private boolean isOwner(Producer producer, long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement isOwner
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }
}
