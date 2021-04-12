package data;

import domain.Production;

public class DataInterface {

    private static DataManager dataManager = new DataManager();

    public static Production getProduction(long ID){
        return dataManager.loadProduction(ID);
    }
}
