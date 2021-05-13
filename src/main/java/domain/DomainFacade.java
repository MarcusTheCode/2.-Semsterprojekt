package domain;

import data.DataFacade;

import java.util.ArrayList;

public class DomainFacade {

    private static System system = new System();

    // Productions

    public static void saveProduction(Production production) {
        try {
            system.saveProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    public static void removeProduction(Production production) {
        try {
            system.deleteProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    public static Production getProduction(int ID) {
        return system.getProduction(ID);
    }

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

    public static ArrayList<SuperUser> getUsers() {
        return DataFacade.getUsers();
    }

    public static SuperUser createUser(String password, String username, boolean sysAdmin) {
        return system.createUser(password, username, sysAdmin);
    }

    public static void editUser(SuperUser superUser) {
        // TODO: Implement
    }

    public static void deleteSuperUser(int ID) {
        DataFacade.deleteSuperUser(ID);
    }

    // Current user

    public static SuperUser getCurrentUser() {
        return system.getCurrentUser();
    }

    public static boolean login(String inputUsername, String inputPassword) {
        return system.login(inputUsername, inputPassword);
    }

    public static void logout() {
        system.logout();
    }
}

