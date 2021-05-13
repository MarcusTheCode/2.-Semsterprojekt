package domain;

import data.DataFacade;

import java.util.ArrayList;

public class DomainFacade {

    private static System system = new System();

    // Productions

    /**
     * This method is used to insert a production into the database.
     * @param production The production to save
     */
    public static void saveProduction(Production production) {
        try {
            system.saveProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    /**
     * This method is used to delete a production from the database.
     * @param production The production to delete
     */
    public static void deleteProduction(Production production) {
        try {
            system.deleteProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    /**
     * This method is used to retrieve a production with the given ID.
     * @param ID The ID of the production
     * @return Production Returns the production.
     */
    public static Production getProduction(int ID) {
        return system.getProduction(ID);
    }

    /**
     * This method is used to edit a production in the database.
     * @param production The production to update
     */
    public static void editProduction(Production production) {
        try {
            system.editProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Artists

    /**
     * This method is used to create a unique artist in the database.
     * @param name The name of the artist to insert into the database
     * @return Artist Returns the newly created artist.
     */
    public static Artist createArtist(String name) {
        Artist artist = new Artist(name);
        DataFacade.insertArtist(artist);
        return artist;
    }

    /**
     * This method is used to delete an artist from the database.
     * @param artistID The ID of the artist to delete from the database
     */
    public static void deleteArtist(int artistID) {
        DataFacade.deleteArtist(artistID);
    }

    /**
     * This method is used to edit an Artist.
     * @param artist The Artist to edit
     */
    public static void editArtist(Artist artist) {
        DataFacade.editArtist(artist);
    }

    /**
     * This method is used to retrieve a list of artists from the database.
     * @return ArrayList<Artist> Returns all artists from the database.
     */
    public static ArrayList<Artist> getArtists() {
        return DataFacade.getArtists();
    }

    // SuperUsers

    /**
     * This method is used to create a unique artist in the database.
     * @return ArrayList<SuperUser> Returns a list of all SuperUsers.
     */
    public static ArrayList<SuperUser> getUsers() {
        return DataFacade.getUsers();
    }

    /**
     * This method is used to create a SuperUser and save it in the database.
     * @param password The password for this user
     * @param username The name of the SuperUser
     * @param sysAdmin Whether this SuperUser is a system administrator
     * @return SuperUser Returns the newly created SuperUser.
     */
    public static SuperUser createUser(String password, String username, boolean sysAdmin) {
        return system.createUser(password, username, sysAdmin);
    }

    /**
     * This method is used to update an existing SuperUser in the database.
     * @deprecated Not up to date with current codebase.
     * @param superUser The SuperUser to update
     */
    public static void editUser(SuperUser superUser) {
        // TODO: Implement
    }

    /**
     * This method is used to delete a SuperUser from the database.
     * @param userID The ID of the SuperUser
     */
    public static void deleteSuperUser(int userID) {
        DataFacade.deleteSuperUser(userID);
    }

    // Current user

    /**
     * This method is used to retrieve the currently logged in SuperUser.
     * @return SuperUser Returns the current SuperUser or null.
     */
    public static SuperUser getCurrentUser() {
        return system.getCurrentUser();
    }

    /**
     * This method is used to log the user in.
     * @param inputUsername The username to login as
     * @param inputPassword The password to match against
     * @return boolean Whether the username and password match.
     */
    public static boolean login(String inputUsername, String inputPassword) {
        return system.login(inputUsername, inputPassword);
    }

    /**
     * This method is used to log the user out of the system.
     */
    public static void logout() {
        system.logout();
    }
}

