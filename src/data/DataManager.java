package data;

import domain.Production;
import domain.SuperUser;

import java.io.*;
import java.util.ArrayList;

import domain.*;
import presentation.*;

public class DataManager {

    private final String DATAFOLDER = "resources/TXT/";
    private File productionsFile;
    private File superUsersFile;

    private ArrayList<SuperUser> users;
    private ArrayList<Production> productions;

    public DataManager() throws Exception {

        this.productionsFile = new File(DATAFOLDER + "Productions.txt");
        this.superUsersFile = new File(DATAFOLDER + "SuperUsers.txt");
    }

    public boolean saveProduction(Production production) {
        if (productionsFile.exists() == false){
            return write(production);
        } else {
            return appendWrite(production);
        }
    }

    private boolean write(Production production){
        // Ever time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (1/2)
        try {
            FileOutputStream fStream = new FileOutputStream(productionsFile);
            ObjectOutputStream oStream = new ObjectOutputStream(fStream);
            oStream.writeObject(production);
            oStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean appendWrite(Production production){
        // Ever time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (2/2)
        try {
            FileOutputStream fStream = new FileOutputStream(productionsFile);
            AppendingObjectOutputStream oStream = new AppendingObjectOutputStream(fStream);
            oStream.writeObject(production);
            oStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
