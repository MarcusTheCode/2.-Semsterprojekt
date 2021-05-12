package domain;

import data.*;

public class System {

    private static SuperUser currentUser;

    public System() {

    }

    public Production createProduction(int ownerID, String title, String category) {
        Production production = new Production(ownerID, 0, title, category);
        saveProduction(production);
        return production;
    }

    public void saveProduction(Production production) {
        DataFacade.insertProduction(production);
    }

    public void deleteProduction(Production production) {
        if (production.isOwner(currentUser) || currentUser.isSysAdmin()) {
            DataFacade.deleteProduction(production.getId());
        } else {
            throw new RuntimeException("ERROR: user is not allowed to edit");
        }
    }

    public void editProduction(Production production) {
        if (production.isOwner(currentUser)) {
            DataFacade.editProduction(production);
        } else {
            throw new RuntimeException("User is not allowed to edit this production");
        }
    }

    public Production getProduction(int ID) {
        return DataFacade.getProduction(ID);
    }

    public SuperUser createUser(String password, String username, boolean sysAdmin) {
        SuperUser user = new SuperUser(0, password, username, sysAdmin);
        saveSuperUser(user);
        return user;
    }

    public void saveSuperUser(SuperUser superUser) {
        DataFacade.insertSuperUser(superUser);
    }

    public void deleteSuperUser(int userID) {
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

    public void logout() {
       currentUser = null;
    }

}
