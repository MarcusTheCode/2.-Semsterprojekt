package data;

import domain.Production;
import domain.SuperUser;

import java.io.*;
import java.util.ArrayList;

public class DataManager {

    private final String DATAFOLDER = "resources/TXT/";
    private final File productionsFile;
    private final File superUsersFile;
    ObjectInputStream objectInputStream;

    private ArrayList<SuperUser> users;
    private ArrayList<Production> productions;

    public DataManager() {
        //Idea: instantiate File streams when creating the DataManager.
        this.productionsFile = new File(DATAFOLDER + "Productions.txt");
        this.superUsersFile = new File(DATAFOLDER + "SuperUsers.txt");
    }

    public boolean saveProduction(Production production) {
        if (!productionsFile.exists()) {
            return write(production);
        } else {
            return appendWrite(production);
        }
    }

    private boolean write(Production production){
        // Ever time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (1/4)
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
        // Every time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (2/4)
        try {
            FileOutputStream fStream = new FileOutputStream(productionsFile, true);
            AppendingObjectOutputStream oStream = new AppendingObjectOutputStream(fStream);
            oStream.writeObject(production);
            oStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Production loadProduction(long proID) {
        // Every time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (3/4)
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(productionsFile));
            while (true) {
                Production snProduction = (Production)objectInputStream.readObject();
                if (snProduction.getId() == proID) {
                    objectInputStream.close();
                    return snProduction;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Production> loadAllProductions() {
        // Every time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (4/4)
        ArrayList<Production> productionArrayList = new ArrayList<>();
        try {
            FileInputStream fStream = new FileInputStream(productionsFile);
            ObjectInput oStream = new ObjectInputStream(fStream);
            // read() returns -1 when end of stream is reached
            while (oStream.read() != -1) {
                Production production = (Production) oStream.readObject(); // object is removed from stream when read
                productionArrayList.add(production);
            }
            oStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return productionArrayList;
    }

    public boolean deleteProductionsFile(){
        return productionsFile.delete();
    }

    public boolean saveSuperUser(SuperUser user) {
        // Every time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (5/4)
        try {
            if (!superUsersFile.exists()) {
                FileOutputStream fStream = new FileOutputStream(superUsersFile);
                ObjectOutputStream oStream = new ObjectOutputStream(fStream);
                oStream.writeObject(user);
                oStream.close();
            } else {
                FileOutputStream fStream = new FileOutputStream(superUsersFile, true);
                AppendingObjectOutputStream oStream = new AppendingObjectOutputStream(fStream);
                oStream.writeObject(user);
                oStream.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SuperUser loadSuperUser(long userID) {
        // Every time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (6/4)
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(superUsersFile));
            while (true) {
                SuperUser superUser = (SuperUser)objectInputStream.readObject();
                if (superUser.getId() == userID){
                    objectInputStream.close();
                    return superUser;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteSuperUser(long userID) {
        // I dont know how it works(if it works). dont touch it!
        // Every time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (7/4)
        try {
            ArrayList<SuperUser> superUsers = new ArrayList<>();
            objectInputStream = new ObjectInputStream(new FileInputStream(superUsersFile));
            while (objectInputStream.available() > 0) {
                SuperUser superUser = (SuperUser)objectInputStream.readObject();
                if (superUser.getId() != userID){
                    superUsers.add(superUser);

                }
            }
            objectInputStream.close();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(superUsersFile));
            for (SuperUser superUser: superUsers) {
                objectOutputStream.writeObject(superUser);
            }
            objectOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
