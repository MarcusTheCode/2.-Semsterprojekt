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

    public long calculateSerialProductionID() {
        long serialProductionID = 0;
        ArrayList<Production> productions = loadAllProductions();
        for (Production production : productions) {
            if (production.getId() > serialProductionID)
                serialProductionID = production.getId();
        }
        return serialProductionID;
    }

    public boolean saveProduction(Production production) {
        if (!productionsFile.exists()) {
            return write(production);
        } else {
            return appendWrite(production);
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

    private boolean reWrite(ArrayList<Production> productions){
        // Ever time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (1/4)
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
        // Every time the program writes to a file, new streams are created, that's inefficient
        // TODO: fix inefficiency (8/4)
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
