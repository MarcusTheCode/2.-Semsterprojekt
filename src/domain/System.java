package domain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import jdk.jshell.spi.ExecutionControl;

import data.*;

import java.util.ArrayList;

public class System extends Application {

    DataManager dataManager;
    SuperUser superUser;

    public System() {
        try {
            this.dataManager = new DataManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.superUser = null;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/Startup.fxml"));
        primaryStage.setTitle("title");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public Production getProduction(long ID) {
        return dataManager.loadProduction(ID);
    }

    public boolean addProduction(Production production) {
        return dataManager.saveProduction(production);
    }

    public boolean editProduction(Production production) throws Exception {
        // check if production ID belongs to a production
        if (!productionIDIsValid(production.getId())) {
            throw new RuntimeException("ERROR: production doesn't exist");
        }
        // check type of logged in user
        switch (this.superUser.getClass().getName()) {
            case "domain.SystemAdministrator":
                break;
            case "domain.Producer":
                if (isOwner((Producer)this.superUser, production.getId())) {
                    break;
                } else {
                    throw new RuntimeException("ERROR: Producer doesn't own that production");
                }
            default:
                throw new RuntimeException("ERROR: current SuperUser is invalid or null");
        }
        // remove the old version of the production and add the new one
        boolean removeSuccess = removeProduction(production.getId());
        boolean addSuccess = addProduction(production);

        return (removeSuccess && addSuccess); // 1: success, 0: failure
    }

    public boolean removeProduction(long ID) throws Exception {
        if (!productionIDIsValid(ID)) {
            throw new RuntimeException("ERROR: production doesn't exist");
        }
        ArrayList<Production> productionArrayList = this.dataManager.loadAllProductions();

        productionArrayList.removeIf(production -> production.getId() == ID);

        dataManager.deleteProductionsFile();

        if (canEdit(ID)) {
            for (Production production: productionArrayList) {
                boolean addSuccess = this.dataManager.saveProduction(production);
                if (!addSuccess) {
                    return false;
                }
            }
        } else {
            throw new RuntimeException("ERROR: Producer doesn't own that production");
        }

        return true;
    }

    public boolean logIn(String username, String password) throws ExecutionControl.NotImplementedException {
        // TODO: implement logIn
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean logOut() throws ExecutionControl.NotImplementedException {
        // TODO: implement logOut
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean saveSuperUser(SuperUser superUser) {
        return dataManager.saveSuperUser(superUser);
    }

    public boolean removeSuperUser(long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement removeSuperUser
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    private boolean isSysAdmin(long ID) {
        SuperUser user = dataManager.loadSuperUser(ID);
        if (user != null)
            return user.isSysAdmin();
        return false;
    }

    private boolean productionIDIsValid(long ID) {
        return dataManager.loadProduction(ID) != null;
    }

    private boolean isOwner(Producer producer, long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement isOwner
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    private boolean canEdit(long id) throws ExecutionControl.NotImplementedException {
        String usrName = this.superUser.getClass().getName();

        if (usrName == "domain.SystemAdministrator"){
            return true;
        }else if(usrName == "domain.Producer"){
            if (isOwner((Producer)this.superUser, id)){
                return true;
            } else {
                throw new RuntimeException("ERROR: Producer doesn't own that production");
            }
        }else {
            throw new RuntimeException("ERROR: current SuperUser is invalid or null");
        }
    }
}
