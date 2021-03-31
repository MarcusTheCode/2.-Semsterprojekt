package domain;

import java.util.ArrayList;
// file reader & File Writer

public class DataManager {

    private reader FileReader;
    private writer FileWriter;
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
