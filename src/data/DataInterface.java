package data;

import domain.Production;
import domain.SuperUser;
import jdk.jshell.spi.ExecutionControl;

public class DataInterface {

    private static DataManager dataManager = new DataManager();

    public static Production getProduction(long ID){
        return dataManager.loadProduction(ID);
    }

    public static boolean saveProduction(Production production){
        return dataManager.saveProduction(production);
    }

    public static boolean editProduction(Production production) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
        // TODO: move functionality from System.editProduction to here
    }

    public static boolean removeProduction(Production production) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not implemented");
        // TODO: move functionality from System.removeProduction to here
    }

    public static boolean saveUser(SuperUser superUser){
        return dataManager.saveSuperUser(superUser);
    }

    public static boolean removeSuperUser(long ID) throws ExecutionControl.NotImplementedException {
        // TODO: implement removeSuperUser
        throw new ExecutionControl.NotImplementedException("Not implemented");
    }

    public static boolean productionIDIsValid(long ID){
        return dataManager.loadProduction(ID) != null;
    }
}
