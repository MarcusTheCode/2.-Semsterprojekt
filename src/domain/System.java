package domain;

import jdk.jshell.spi.ExecutionControl;

import data.*;
import presentation.UIManager;

import java.util.ArrayList;

public class System {

    DataManager dataManager;
    UIManager uiManager;
    SuperUser superUser;

    public System(UIManager uiManager) {
        try {
            this.dataManager = new DataManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.superUser = null;
        this.uiManager = uiManager;

        // DEBUG
        //SuperUser producer = new SuperUser(0, "Henrik", "password123", false);
        //saveSuperUser(producer);

        //Production production = new Production(user.id, 0, "Yeet", "thriller");
        //dataManager.saveProduction(production);

        //superUser = dataManager.loadSuperUser(0);

        //Production production = new Production(superUser.id, 1, "Yeet", "thriller");
        //dataManager.saveProduction(production);

        /*Production production = dataManager.loadProduction(0);
        java.lang.System.out.println(production);

        if (canEdit(production.id)) {
            java.lang.System.out.println("Can Edit");
        } else {
            java.lang.System.out.println("Can't Edit");
        }*/
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
        if (!canEdit(superUser.id)) {
            throw new RuntimeException("User is not allowed to edit this production");
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

    private boolean isOwner(SuperUser producer, long id) {
        Production production = dataManager.loadProduction(id);
        if (production.isOwner(producer)) {
            return true;
        }
        return false;
    }

    private boolean canEdit(long id) {
        return isOwner(superUser, id) || superUser.isSysAdmin();
    }
}
