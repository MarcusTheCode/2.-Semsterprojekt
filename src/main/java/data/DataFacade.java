package data;

import domain.Production;
import domain.SuperUser;

import java.util.ArrayList;

public class DataFacade {

    private static DatabaseManager databaseManager = new DatabaseManager();
    //private static DataManager dataManager = new DataManager();

    public static Production getProduction(int ID){
        return databaseManager.getProduction(ID);
    }

    public static void saveProduction(Production production) {
        databaseManager.insertPrduction(production);
    }

    public static void deleteProduction(int ID) {
        databaseManager.deleteProduction(ID);
    }

    public static void editProduction(Production production){
        // TODO: Implement
        //databaseManager.editProduction(production);
    }

    public static SuperUser getSuperUser(int ID){
        return databaseManager.getSuperUser(ID);
    }

    public static void saveSuperUser(SuperUser superUser) {
        databaseManager.insertSuperUser(superUser);
    }

    public static void deleteSuperUser(int userID) {
        databaseManager.deleteSuperUser(userID);
    }

    public static ArrayList<SuperUser> getUsers() {
        return databaseManager.getSuperUsers();
    }

    public static SuperUser login(String inputUsername, String inputPassword) {
        return databaseManager.checkIfUserExists(inputUsername, inputPassword);
    }

    public static ArrayList<Production> loadAllProductions(){
        return databaseManager.getAllProductions();
    }

    public static int getSeasonNumber(int ID){
        return databaseManager.getSeasonNumber(ID);
    }

    public static ArrayList<String> getGenres(int productionID){
        return databaseManager.getGenres(productionID);
    }
}
