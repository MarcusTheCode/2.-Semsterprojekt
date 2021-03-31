package data;

import domain.Production;
import domain.SuperUser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class DataManager {

    private FileReader fileReader;
    private FileWriter fileWriter;
    public final String DATAFOLDER = "TEST";

    /*
    private File productionFile;
    private File superUserFile
    */

    private ArrayList<SuperUser> users;
    private ArrayList<Production> productions;

    public boolean saveProduction(Production pro) {
        return true;
    }

    public Production loadProduction(Production pro) {
        return pro;
    }

    public boolean saveSuperUser(SuperUser user) {
        return true;
    }

    public SuperUser loadSuperUser(SuperUser username) {
        return username;
    }


}
