package domain;

import jdk.jshell.spi.ExecutionControl;

import data.*;
import presentation.LogInController;
import presentation.UIManager;

public class System {

    private static SuperUser superUser;

    private long serialUserID;
    private long serialProductionID;

    public System() {
        serialUserID = DataInterface.calculateSerialUserID();
        serialProductionID = DataInterface.calculateSerialProductionID();

        //SuperUser user = new SuperUser(0, "pass123", "Ikke Albert", true);
        //DataInterface.saveSuperUser(user);

        //SuperUser user = DataInterface.getSuperUser(0);
        //java.lang.System.out.println(user);
    }

    public void createProduction(long ownerID, String title, String category) {
        serialProductionID++;
        Production production = new Production(ownerID, serialProductionID, title, category);
        saveProduction(production);
    }

    public void saveProduction(Production production) {
        DataInterface.saveProduction(production);
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
            saveProduction(production);
            return;
        }
        throw new RuntimeException("User is not allowed to edit this production");
    }

    public Production getProduction(long ID) {
        return DataInterface.getProduction(ID);
    }

    public void createUser(String password, String username, boolean sysAdmin) {
        serialUserID++;
        SuperUser user = new SuperUser(serialUserID, password, username, sysAdmin);
        saveSuperUser(user);
    }

    public void saveSuperUser(SuperUser superUser) {
        DataInterface.saveSuperUser(superUser);
    }

    public void deleteSuperUser(long ID) {
        DataInterface.deleteSuperUser(ID);
    }

    protected static SuperUser getSuperUser() {
        return superUser;
    }


    //missing failure message when login is incorrect
    public boolean logIn(String inputUsername, String inputPassword)  {
        superUser = DataInterface.checkIfUserExists(inputUsername, inputPassword);
        return superUser != null;
    }

    public boolean logOut() throws ExecutionControl.NotImplementedException {
        // TODO: implement logOut
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

}
