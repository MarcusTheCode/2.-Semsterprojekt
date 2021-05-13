package domain;

import data.DataFacade;

import java.util.ArrayList;

public class DomainFacade {

    private static System system = new System();

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

    public static void createArtist(String name) {

    }

    /**
     * This method is used to retrieve a list of artists from the database.
     * @return ArrayList<Artist> Returns all artists from the database.
     */
    public static ArrayList<Artist> getArtists() {
        return DataFacade.getArtists();
    }

    public static SuperUser getCurrentUser() {
        return system.getCurrentUser();
    }

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

    public static boolean login(String inputUsername, String inputPassword) {
        return system.login(inputUsername, inputPassword);
    }

    public static void logout() {
        system.logout();
    }
}

