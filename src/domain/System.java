package domain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import jdk.jshell.spi.ExecutionControl;

import data.*;
import presentation.*;

import java.util.ArrayList;

public class System extends Application {

    DataManager dataManager;
    SuperUser superUser;

    public System() {
        // TODO: (fix bug) the constructor is for some reason called twice
        try {
            this.dataManager = new DataManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.superUser = null;
    }

    public static void main(String[] args) {
        System system = new System();
        system.launch();
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
        if (productionIDIsValid(production.getId()) == false){
            throw new RuntimeException("ERROR: production doesn't exist");
        }
        // check type of logged in user
        //Could be removed
        /*switch(this.superUser.getClass().getName()) {
            case "domain.SystemAdministrator":
                break;
            case "domain.Producer":
                if (isOwner((Producer)this.superUser, production.getId())){
                    break;
                } else {
                    throw new RuntimeException("ERROR: Producer doesn't own that production");
                }
            default:
                throw new RuntimeException("ERROR: current SuperUser is invalid or null");
        }*/
        // remove the old version of the production and add the new one
        boolean removeSuccess = removeProduction(production.getId());
        boolean addSuccess = addProduction(production);

        return (removeSuccess && addSuccess); // 1: success, 0: failure
    }

    public boolean removeProduction(long ID) throws Exception {
        if (productionIDIsValid(ID) == false){
            throw new RuntimeException("ERROR: production doesn't exist");
        }
        ArrayList<Production> productionArrayList = this.dataManager.loadAllProductions();

        // check type of logged in user
        switch(this.superUser.getClass().getName()) {
            case "domain.SystemAdministrator":
                for (Production production: productionArrayList){
                    if (production.getId() == ID){
                        productionArrayList.remove(production);
                    }
                }

                dataManager.deleteProductionsFile();

                for (Production production: productionArrayList){
                    boolean addSuccess = this.dataManager.saveProduction(production);
                    if (addSuccess == false){
                        return false;
                    }
                }
                return true;
            case "domain.Producer":
                if (isOwner((Producer)this.superUser, ID)){
                    for (Production production: productionArrayList){
                        if (production.getId() == ID){
                            productionArrayList.remove(production);
                        }
                    }

                    dataManager.deleteProductionsFile();

                    for (Production production: productionArrayList){
                        boolean addSuccess = this.dataManager.saveProduction(production);
                        if (addSuccess == false){
                            return false;
                        }
                    }
                    return true;
                } else {
                    throw new RuntimeException("ERROR: Producer doesn't own that production");
                }
            default:
                throw new RuntimeException("ERROR: current SuperUser is invalid or null");
        }
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

    private boolean productionIDIsValid(long ID) {
        if (dataManager.loadProduction(ID) == null){
            return false;
        } else {
            return true;
        }
    }

    private boolean isOwner(Producer producer, long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement isOwner
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }
}
