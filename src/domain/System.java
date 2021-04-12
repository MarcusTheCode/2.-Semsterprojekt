package domain;

import jdk.jshell.spi.ExecutionControl;

import data.*;
import presentation.UIManager;

public class System {

    UIManager uiManager;
    private static SuperUser superUser;

    public System(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    public boolean addProduction(Production production) {
        return DataInterface.saveProduction(production);
    }

    public void removeProduction(Production production) throws Exception {
        if (production.isOwner(superUser) || superUser.isSysAdmin()) {
            DataInterface.deleteProduction(production.getId());
            return;
        }
        throw new RuntimeException("ERROR: SuperUser is not allowed to edit");
    }

    public void editProduction(Production production) throws Exception {
        if (production.isOwner(superUser)) {
            removeProduction(production);
            addProduction(production);
            return;
        }
        throw new RuntimeException("User is not allowed to edit this production");
    }

    public Production getProduction(long ID) {
        return DataInterface.getProduction(ID);
    }

    public void saveSuperUser(SuperUser superUser) throws Exception{
        DataInterface.saveSuperUser(superUser);
    }

    public void deleteSuperUser(long ID) throws Exception {
        DataInterface.deleteSuperUser(ID);
    }

    protected static SuperUser getSuperUser() {
        return superUser;
    }

    public boolean logIn(String username, String password) throws ExecutionControl.NotImplementedException {
        // TODO: implement logIn
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public boolean logOut() throws ExecutionControl.NotImplementedException {
        // TODO: implement logOut
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }
}
