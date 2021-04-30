package domain;

import data.*;

public class System {

    private static SuperUser currentUser;

    private long serialUserID;
    private long serialProductionID;

    public System() {
        serialUserID = DataFacade.calculateSerialUserID();
        serialProductionID = DataFacade.calculateSerialProductionID();

        //SuperUser user = new SuperUser(0, "pass123", "Ikke Albert", true);
        //DataInterface.saveSuperUser(user);

        //SuperUser user = DataInterface.getSuperUser(0);
        //java.lang.System.out.println(user);
    }

    public Production createProduction(long ownerID, String title, String category) {
        serialProductionID++;
        Production production = new Production(ownerID, serialProductionID, title, category);
        saveProduction(production);
        return production;
    }

    public void saveProduction(Production production) {
        DataFacade.saveProduction(production);
    }

    public void deleteProduction(Production production) throws Exception {
        if (production.isOwner(currentUser) || currentUser.isSysAdmin()) {
            DataFacade.deleteProduction(production.getId());
            return;
        } else {
            throw new RuntimeException("ERROR: user is not allowed to edit");
        }
    }

    public void editProduction(Production production) throws Exception {
        if (production.isOwner(currentUser)) {
            DataFacade.editProduction(production);
            return;
        } else {
            throw new RuntimeException("User is not allowed to edit this production");
        }
    }

    public Production getProduction(long ID) {
        return DataFacade.getProduction(ID);
    }

    public SuperUser createUser(String password, String username, boolean sysAdmin) {
        serialUserID++;
        SuperUser user = new SuperUser(serialUserID, password, username, sysAdmin);
        saveSuperUser(user);
        return user;
    }

    public void saveSuperUser(SuperUser superUser) {
        DataFacade.saveSuperUser(superUser);
    }

    public void deleteSuperUser(long userID) {
        DataFacade.deleteSuperUser(userID);
    }

    public SuperUser getCurrentUser() {
        return currentUser;
    }


    //missing failure message when login is incorrect
    public boolean login(String inputUsername, String inputPassword)  {
        currentUser = DataFacade.login(inputUsername, inputPassword);
        return currentUser != null;
    }

    public boolean logout() {
       currentUser = null;
       return currentUser == null;
    }

}
