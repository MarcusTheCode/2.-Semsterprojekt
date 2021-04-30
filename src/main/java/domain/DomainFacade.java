package domain;

import data.DataFacade;

import java.util.ArrayList;

public class DomainFacade {

    private static System system = new System();

    public static void saveProduction(Production production){
        try {
            system.saveProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    public static void removeProduction(Production production){
        try {
            system.deleteProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    public static Production getProduction(long ID){
        return system.getProduction(ID);
    }

    public static void editProduction(Production production){
        try {
            system.editProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    public static SuperUser getCurrentUser(){
        return system.getCurrentUser();
    }

    public static ArrayList<SuperUser> getUsers() {
        return DataFacade.getUsers();
    }

    public static SuperUser createUser(String password, String username, boolean sysAdmin) {
        return system.createUser(password, username, sysAdmin);
    }

    public static void saveUser(SuperUser user) {
        DataFacade.saveSuperUser(user);
    }

    public static void editUser(SuperUser user) {
        deleteUser(user.getId());
        saveUser(user);
    }

    public static void deleteUser(long id) {
        system.deleteSuperUser(id);
    }

    public static System getSystem() {
        return system;
    }

    public static boolean login(String inputUsername, String inputPassword) {
        return system.login(inputUsername, inputPassword);
    }

    public static boolean logout(){
        return system.logout();
    }
}
