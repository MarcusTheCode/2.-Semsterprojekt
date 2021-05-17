package domain;

import data.*;

public class System {

    private static SuperUser currentUser;

    public System() {

    }

    /**
     * This method is used to insert a production into the database.
     * @param production The production to save
     */
    public void saveProduction(Production production) {
        DataFacade.insertProduction(production);
    }

    /**
     * This method is used to delete a production from the database.
     * @param production The production to delete
     */
    public void deleteProduction(Production production) {
        if (production.isOwner(currentUser) || currentUser.isSysAdmin()) {
            DataFacade.deleteProduction(production.getId());
        } else {
            throw new RuntimeException("ERROR: user is not allowed to edit");
        }
    }

    /**
     * This method is used to edit a production
     * @deprecated Not up to date with current codebase
     * @param production The production to edit
     */
    public void editProduction(Production production) {
        if (production.isOwner(currentUser)||currentUser.isSysAdmin()) {
            DataFacade.editProduction(production);
        } else {
            throw new RuntimeException("User is not allowed to edit this production");
        }
    }

    /**
     * This method is used to retrieve a production with the given ID.
     * @param productionID The ID of the production
     * @return Production Returns the production or null.
     */
    public Production getProduction(int productionID) {
        return DataFacade.getProduction(productionID);
    }

    /**
     * This method is used to create a SuperUser from the given parameters.
     * @param password The password of the SuperUser
     * @param username The username of the SuperUser
     * @param sysAdmin Whether the SuperUser is a system administrator
     * @return SuperUser Returns the SuperUser.
     */
    public SuperUser createUser(String password, String username, boolean sysAdmin) {
        SuperUser user = new SuperUser(0, password, username, sysAdmin);
        saveSuperUser(user);
        return user;
    }

    /**
     * This method is used to insert a SuperUser into the database.
     * @param superUser The SuperUser to save
     */
    public void saveSuperUser(SuperUser superUser) {
        DataFacade.insertSuperUser(superUser);
    }

    /**
     * This method is used to delete a SuperUser from the database.
     * @param userID The ID of the SuperUser
     */
    public void deleteSuperUser(int userID) {
        DataFacade.deleteSuperUser(userID);
    }

    /**
     * This method is used to retrieve the currently logged in SuperUser.
     * @return SuperUser Returns the current SuperUser or null if not logged in.
     */
    public SuperUser getCurrentUser() {
        return currentUser;
    }

    /**
     * This method is used to log the user in.
     * @param inputUsername The username to login as
     * @param inputPassword The password to match against
     * @return boolean Whether the username and password match.
     */
    public boolean login(String inputUsername, String inputPassword)  {
        currentUser = DataFacade.login(inputUsername, inputPassword);
        return currentUser != null;
    }

    /**
     * This method is used to log the user out of the system.
     */
    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }

    public static void saveUserChanges(SuperUser superUser) {
        DataFacade.saveUserChanges(superUser);
    }
}
