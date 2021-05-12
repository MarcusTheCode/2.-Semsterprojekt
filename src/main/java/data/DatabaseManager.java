package data;

import domain.Production;
import domain.SuperUser;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    // Structure of data for file credentials
    //url, port, databaseName, username, password){

    //Database variables
    private String url;
    private int port;
    private String databaseName;
    private String username;
    private String password;
    private Connection connection = null;

    public DatabaseManager() {
        try {
            FileReader fileReader = new FileReader("resources/TXT/DatabaseCredentials");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            this.url = bufferedReader.readLine();
            this.port = Integer.parseInt(bufferedReader.readLine());
            this.databaseName = bufferedReader.readLine();
            this.username = bufferedReader.readLine();
            this.password = bufferedReader.readLine();
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection == null) System.exit(-1);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    // insert methods
    public boolean insertPrduction(Production production){
            try {

                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO productions(episodeNumber, type,categoryID,seasonsID," +
                                "producerID, productionTitle)VALUES (?,?,?,?,?)");
                ps.setInt(1,production.getEpisodeNumber());
                ps.setString(2, production.getType());
                ps.setInt(3, getCategoryID(production));
                ps.setLong(4,production.getOwnerID());
                ps.setString(5,production.getTitle());
                return ps.execute();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return false;
    }

    public boolean insertSuperUser(SuperUser user){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO superUsers(isAdmin, userName,passWord,)"
                    +"VALUES (?,?,?)");
            ps.setBoolean(1,user.isSysAdmin());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getPassword());
            return ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean insertSeries(String name){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO series(name)+" +
                    "VALUES(?)");
            ps.setString(1,name);
            return ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    //TODO: Optimize method
    public boolean insertProductionGenre(Production production, int genreID){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO productionGenres(productionID,genreID)+" +
                    "VALUES(?,?)");
            ps.setInt(1,production.getId());
            ps.setInt(2, genreID);

            return ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean insertGenre(Production production){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO genres (name) VALUES (?)");
            ps.setArray(1, connection.createArrayOf("String",production.getGenres().toArray()));
            return ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int insertCategory(Production production){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO categories (name) VALUES (?)");
            ps.setString(1,production.getCategory().toLowerCase());
            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return getCategoryID(production);
    }

    // read methods

    public Production getProduction(int productionID){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM productions WHERE productions.id = ?");
            ps.setInt(1,productionID);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new Production(
                    resultSet.getInt(2),
                    resultSet.getInt(5),
                    resultSet.getInt(6),
                    resultSet.getInt(1),
                    resultSet.getString(7),
                    getCategoryID(resultSet.getInt(4)),
                    resultSet.getString(3));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public SuperUser getSuperUser(int usrID){
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM superUsers WHERE superUsers.id = ?");
            ps.setInt(1,usrID);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return new SuperUser(
                    resultSet.getInt(1),
                    resultSet.getString(4),
                    resultSet.getString(3),
                    resultSet.getBoolean(2));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteSuperUser(int userID){
        try{
            PreparedStatement ps = connection.prepareStatement("DELETE FROM superUsers WHERE superUsers.id = ?");
            ps.setInt(1,(int)userID);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduction(long userID){
        try{
            PreparedStatement ps = connection.prepareStatement("DELETE FROM productions WHERE productions.id = ?");
            ps.setInt(1, (int)userID);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public SuperUser checkIfUserExists(String inputUsername, String inputPassword){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM superUsers WHERE superUsers.userName = ?" +
                    "AND superUsers.passWord = ?");
            ps.setString(1, inputUsername);
            ps.setString(2, inputPassword);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return getSuperUser(resultSet.getInt(1));
            }else {
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    // get methods
    public int getCategoryID(Production production){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT getCategoryID(?)");
            ps.setString(1,production.getCategory().toLowerCase());
            ResultSet set = ps.executeQuery();
            if (set.next()){
                return set.getInt(1);
            }else{
                return insertCategory(production);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public String getCategoryID(int id){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT categories.name FROM categories " +
                    "WHERE categories.id = ?");
            ps.setInt(1,id);
            ResultSet set = ps.executeQuery();
            set.next();
            return set.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList<Production> getAllProductions() {
        ArrayList<Production> productions = new ArrayList<>();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM getAllProductions()");
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                Production p = new Production(
                        resultSet.getInt(2),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(1),
                        resultSet.getString(7),
                        getCategoryID(resultSet.getInt(4)),
                        resultSet.getString(3));
                productions.add(p);
            }
            return productions;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<SuperUser> getSuperUsers() {
        ArrayList<SuperUser> users = new ArrayList<>();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM superUsers");
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()) {
                users.add(new SuperUser(
                        resultSet.getInt(1),
                        resultSet.getString(4),
                        resultSet.getString(3),
                        resultSet.getBoolean(2)));

            }
            return users;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
