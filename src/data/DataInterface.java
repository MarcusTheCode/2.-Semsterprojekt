package data;

import domain.Production;
import domain.SuperUser;

import java.util.ArrayList;

public class DataInterface {

    private static DataManager dataManager = new DataManager();

    public static Production getProduction(long ID){
        return dataManager.loadProduction(ID);
    }

    public static boolean saveProduction(Production production) {
        return dataManager.saveProduction(production);
    }

    public static boolean deleteProduction(long ID) {
        ArrayList<Production> productionArrayList = dataManager.loadAllProductions();
        productionArrayList.removeIf(production -> production.getId() == ID);

        if (!dataManager.deleteProductionsFile())
            return false;

        for (Production production: productionArrayList) {
            if (!dataManager.saveProduction(production))
                return false;
        }

        return true;
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

    public static SuperUser checkIfUserExists(String inputUsername, String inputPassword) {
        dataManager.checkIfUserExists(inputUsername, inputPassword);
        return dataManager.checkIfUserExists(inputUsername, inputPassword);
    }

    public static ArrayList<Production> loadAllProductions(){
        return dataManager.loadAllProductions();
    }
}
