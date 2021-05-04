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

    public DataManager() {
        //Idea: instantiate File streams when creating the DataManager.
        this.productionsFile = new File(DATAFOLDER + "Productions.txt");
        this.superUsersFile = new File(DATAFOLDER + "SuperUsers.txt");
    }

    public long calculateSerialUserID() {
        long serialUserID = 0;
        ArrayList<SuperUser> users = loadSuperUsers();
        for (SuperUser user : users) {
            if (user.getId() > serialUserID)
                serialUserID = user.getId();
        }
        return serialUserID;
    }

    public int calculateSerialProductionID() {
        int serialProductionID = 0;
        ArrayList<Production> productions = loadAllProductions();
        for (Production production : productions) {
            if (production.getId() > serialProductionID)
                serialProductionID = production.getId();
        }
        return serialProductionID;
    }

    public void saveProduction(Production production) {
        if (!productionsFile.exists()) {
            write(production);
        } else {
            appendWrite(production);
        }
    }

    public void deleteProduction(long ID) {
        ArrayList<Production> productions = loadAllProductions();
        productions.removeIf(production -> production.getId() == ID);
        reWrite(productions);
    }

    public void editProduction(Production production) {
        ArrayList<Production> productions = loadAllProductions();
        productions.removeIf(productionElement -> productionElement.getId() == production.getId());
        productions.add(production);
        reWrite(productions);
    }

    // in databasemanager
    private boolean write(Production production){
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


    private boolean reWrite(ArrayList<Production> productions){
        try {
            FileOutputStream fStream = new FileOutputStream(productionsFile);
            ObjectOutputStream oStream = new ObjectOutputStream(fStream);
            for (Production production: productions){
                oStream.writeObject(production);
            }
            oStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean appendWrite(Production production){
        try {
            FileOutputStream fStream = new FileOutputStream(productionsFile, true);
            data.AppendingObjectOutputStream oStream = new data.AppendingObjectOutputStream(fStream);
            oStream.writeObject(production);
            oStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // in databaseManager
    public Production loadProduction(long proID) {
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

    // in migration
    public ArrayList<Production> loadAllProductions() {
        ArrayList<Production> productionArrayList = new ArrayList<>();
        try {
            FileInputStream fStream = new FileInputStream(productionsFile);
            ObjectInput oStream = new ObjectInputStream(fStream);
            while (fStream.available() > 0) {
                Production production = (Production) oStream.readObject(); // object is removed from stream when read
                if (production!=null){
                    productionArrayList.add(production);
                }
            }
            return productionArrayList;
        } catch (ClassNotFoundException | IOException ignored) {
            return productionArrayList;
        }
    }

    public boolean saveSuperUser(SuperUser user) {
        try {
            if (!superUsersFile.exists()) {
                FileOutputStream fStream = new FileOutputStream(superUsersFile);
                ObjectOutputStream oStream = new ObjectOutputStream(fStream);
                oStream.writeObject(user);
                oStream.close();
            } else {
                FileOutputStream fStream = new FileOutputStream(superUsersFile, true);
                data.AppendingObjectOutputStream oStream = new data.AppendingObjectOutputStream(fStream);
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

    // in DatabaseManager
    public boolean deleteSuperUser(long userID) {
        try {
            ArrayList<SuperUser> superUsers = new ArrayList<>();
            FileInputStream fileStream = new FileInputStream(superUsersFile);
            objectInputStream = new ObjectInputStream(fileStream);
            while (fileStream.available() > 0) {
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

    public ArrayList<SuperUser> loadSuperUsers() {
        ArrayList<SuperUser> users = new ArrayList<>();
        try {
            FileInputStream fileStream = new FileInputStream(superUsersFile);
            objectInputStream = new ObjectInputStream(fileStream);
            while (fileStream.available() > 0) {
                SuperUser superUser = (SuperUser)objectInputStream.readObject();
                users.add(superUser);
            }
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public SuperUser checkIfUserExists(String inputUsername, String inputPassword) {
        ArrayList<SuperUser> superUserArrayList = loadSuperUsers();
        // checks if username and password input matches file username and password
        for (SuperUser user : superUserArrayList) {
            if (inputUsername.equals(user.getUsername()) && inputPassword.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
     }
}
