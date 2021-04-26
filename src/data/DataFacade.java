package data;

import domain.Production;
import domain.SuperUser;

import java.util.ArrayList;

public class DataFacade {

    private static DataManager dataManager = new DataManager();

    public static Production getProduction(long ID){
        return dataManager.loadProduction(ID);
    }

    public static void saveProduction(Production production) {
        dataManager.saveProduction(production);
    }

    public static void deleteProduction(long ID) {
        dataManager.deleteProduction(ID);
    }

    public static void editProduction(Production production){
        dataManager.editProduction(production);
    }

    public static long calculateSerialUserID() {
        return dataManager.calculateSerialUserID();
    }

    public static long calculateSerialProductionID() {
        return dataManager.calculateSerialProductionID();
    }

    public static SuperUser getSuperUser(long ID){
        return dataManager.loadSuperUser(ID);
    }

    public static void saveSuperUser(SuperUser superUser) {
        dataManager.saveSuperUser(superUser);
    }

    public static void deleteSuperUser(long ID) {
        dataManager.deleteSuperUser(ID);
    }

    public static ArrayList<SuperUser> getUsers() {
        return dataManager.loadSuperUsers();
    }

    public static SuperUser login(String inputUsername, String inputPassword) {
        dataManager.checkIfUserExists(inputUsername, inputPassword);
        return dataManager.checkIfUserExists(inputUsername, inputPassword);
    }

    public static ArrayList<Production> loadAllProductions(){
        return dataManager.loadAllProductions();
    }
}
