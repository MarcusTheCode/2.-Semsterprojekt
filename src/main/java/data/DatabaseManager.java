package data;

import domain.*;
import jdk.jshell.spi.ExecutionControl;

import java.io.*;
import java.lang.System;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager {
    // Structure of data for file DatabaseCredentials
    //  url
    //  port
    //  databaseName
    //  username
    //  password

    // Database variables
    private String url;
    private int port;
    private String databaseName;
    private String username;
    private String password;
    private Connection connection = null;

    // Constructor
    public DatabaseManager() {
        try {
            FileReader fileReader = new FileReader("src/main/resources/TXT/DatabaseCredentials");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            this.url = bufferedReader.readLine();
            this.port = Integer.parseInt(bufferedReader.readLine());
            this.databaseName = bufferedReader.readLine();
            this.username = bufferedReader.readLine();
            this.password = bufferedReader.readLine();
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (connection == null) System.exit(-1);
        }

    }

    /**
     * This method returns the PostgreSQL connection.
     * @return Connection Returns the PostgreSQL connection.
     */
    public Connection getConnection() {
        return connection;
    }



    // Production

    /**
     * This method is used insert a new production into the database.
     * @param production The production to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertProduction(Production production) {
        String sqlCode;
        if (production.getSeasonID() == null) {
            sqlCode = "INSERT INTO productions(episodeNumber,type,categoryID,producerID,productionTitle) VALUES (?,?,?,?,?)";
        }else{
            sqlCode = "INSERT INTO productions(episodeNumber,type,categoryID,producerID,productionTitle,seasonID)" +
                    " VALUES (?,?,?,?,?,?)";
        }
        try (PreparedStatement ps = connection.prepareStatement(sqlCode)) {
            ps.setInt(1, production.getEpisodeNumber());
            ps.setString(2, production.getType());
            ps.setInt(3, getCategoryID(production));
            ps.setLong(4, production.getOwnerID());
            ps.setString(5, production.getTitle());
            if (production.getSeasonID() != null) {
                ps.setInt(6, production.getSeasonID());
            }
            ps.execute();
            // If an error occurs when executing the ps, it jumps to the catch statement
            // otherwise, it is safe to assume that the statement executed correctly
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to delete a Production from the database, given an ID.
     * @param productionID The ID of the Production
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean deleteProduction(int productionID) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM productions WHERE productions.id = ?")) {
            ps.setInt(1, productionID);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateProduction(Production production){
        String sqlCode;
        if (production.getSeasonID() == null) {
            sqlCode = "UPDATE productions SET " +
                    "episodeNumber = ?, " +
                    "type = ?, " +
                    "categoryID = ?, " +
                    "producerID = ?, " +
                    "productionTitle = ? " +
                    "WHERE id = ?;";
        } else {
            sqlCode = "UPDATE productions SET " +
                    "episodeNumber = ?, " +
                    "type = ?, " +
                    "categoryID = ?, " +
                    "producerID = ?, " +
                    "productionTitle = ?, " +
                    "seasonID = ? " +
                    "WHERE id = ?; ";
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sqlCode);
            ps.setInt(1,production.getEpisodeNumber());
            ps.setString(2,production.getType());
            ps.setInt(3,production.getCategoryID());
            ps.setInt(4,production.getProducerID());
            ps.setString(5, production.getTitle());
            if (production.getSeasonID() == null) {
                ps.setInt(6, production.getId());
            } else {
                ps.setInt(6,production.getSeasonID());
                ps.setInt(7, production.getId());
            }
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to retrieve a production from the database, given an ID.
     * @param productionID The ID of the production
     * @return Production Returns the production with the ID or null.
     */
    public Production getProduction(int productionID) {
        Production production = null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM productions WHERE productions.id = ?")) {
            ps.setInt(1, productionID);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                production = new Production(
                        resultSet.getInt(2),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(1),
                        resultSet.getString(7),
                        getCategoryID(resultSet.getInt(4)),
                        resultSet.getString(3));
                production.setCastMembers(this.getCastMembers(production.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return production;
    }

    /**
     * This method is used to retrieve all productions from the database.
     * @return ArrayList<Production> Returns a list of all productions.
     */
    public ArrayList<Production> getAllProductions() {
        ArrayList<Production> productions = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM getAllProductions()")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
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
            }
            return productions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used in the persistence tests to retrieve the ID in a non-hacky way
     * @return HashMap with production name as key and ID as value
     */
    public HashMap<String,Integer> getProductionsMap(){
        HashMap<String,Integer> productionMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT productions.productiontitle, productions.id FROM productions")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    productionMap.put(resultSet.getString(1),resultSet.getInt(2));
                }
            }
            return productionMap;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // SuperUser

    /**
     * This method is used insert a new SuperUser into the database.
     * @param superUser The SuperUser to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertSuperUser(SuperUser superUser) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO superUsers(isAdmin,username,password)" +
                "VALUES (?,?,?)")) {
            ps.setBoolean(1, superUser.isSysAdmin());
            ps.setString(2, superUser.getUsername());
            ps.setString(3, superUser.getPassword());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to delete a SuperUser from the database, given an ID.
     * @param userID The ID of the SuperUser
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean deleteSuperUser(int userID) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM superUsers WHERE superUsers.id = ?")) {
            ps.setInt(1, userID);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to edit a SuperUser
     * @param superUserUsername the username of the SuperUser that should be edited
     * @param newSuperUser what the SuperUser with the ID SuperUserID should be edited to
     */
    public void updateSuperUser(String superUserUsername, SuperUser newSuperUser){
        HashMap<String, Integer> superUsersMap = getSuperUsersMap();
        Integer superUserID = superUsersMap.get(superUserUsername);

        String sqlCode;
        sqlCode = "UPDATE superusers SET " +
                "username = ?, " +
                "password = ?, " +
                "isadmin = ? " +
                "WHERE id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sqlCode)) {
            ps.setString(1,newSuperUser.getUsername());
            ps.setString(2,newSuperUser.getPassword());
            ps.setBoolean(3,newSuperUser.isSysAdmin());
            ps.setInt(4,superUserID);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to retrieve the SuperUser with the given username, if the passwords match.
     * @param inputUsername The username to check for
     * @param inputPassword The password of the user to match against
     * @return SuperUser Returns the SuperUser or null, if incorrect.
     */
    public SuperUser checkIfUserExists(String inputUsername, String inputPassword) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM superUsers WHERE superUsers.userName = ? AND superUsers.passWord = ?")) {
            ps.setString(1, inputUsername);
            ps.setString(2, inputPassword);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getSuperUser(resultSet.getInt(1));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to retrieve a SuperUser from the database, given an ID.
     * @param userID The ID of the SuperUser
     * @return SuperUser Returns the SuperUser with the ID or null.
     */
    public SuperUser getSuperUser(int userID) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM superUsers WHERE superUsers.id = ?")) {
            ps.setInt(1, userID);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                return new SuperUser(
                        resultSet.getInt(1),
                        resultSet.getString(4),
                        resultSet.getString(3),
                        resultSet.getBoolean(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is used to retrieve a SuperUser from the database, given a name.
     * @param superUserUsername The username of the SuperUser
     * @return SuperUser Returns the SuperUser with the username or null.
     */
    public SuperUser getSuperUser(String superUserUsername){
        HashMap<String, Integer> superUsersMap = getSuperUsersMap();
        Integer superUserID = superUsersMap.get(superUserUsername);

        if (superUserID == null){
            try {
                throw new Exception("ERROR: no SuperUser with name: " + superUserUsername);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM superUsers WHERE superUsers.id = ?")) {
            ps.setInt(1, superUserID);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                return new SuperUser(
                        resultSet.getInt(1),
                        resultSet.getString(4),
                        resultSet.getString(3),
                        resultSet.getBoolean(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is used to retrieve all SuperUsers from the database.
     * @return ArrayList<SuperUser> Returns a list of all SuperUsers.
     */
    public ArrayList<SuperUser> getSuperUsers() {
        ArrayList<SuperUser> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM superUsers")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    users.add(new SuperUser(
                            resultSet.getInt(1),
                            resultSet.getString(4),
                            resultSet.getString(3),
                            resultSet.getBoolean(2)));
                }
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is used in the persistence tests to retrieve the ID in a non-hacky way
     * @return HashMap with production name as key and ID as value
     */
    public HashMap<String,Integer> getSuperUsersMap(){
        HashMap<String,Integer> superUsersMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT superusers.username, superusers.id FROM superusers")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    superUsersMap.put(resultSet.getString(1),resultSet.getInt(2));
                }
            }
            return superUsersMap;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Artist

    /**
     * This method is used to insert a unique artist into the database.
     * @param artist The artist to insert into the database
     */
    public boolean insertArtist(Artist artist) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO artists(name,email)" +
                "VALUES (?,?)")) {
            ps.setString(1, artist.getName());
            ps.setString(2, artist.getEmail());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to delete an artist from the database.
     * @param artistID The ID of the artist to delete from the database
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean deleteArtist(int artistID) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM artists WHERE artists.id = ?")) {
            ps.setInt(1, artistID);
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to edit an Artist.
     * @param artist The Artist to edit
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean updateArtist(Artist artist) {
        // TODO: Implement
        try {
            throw new ExecutionControl.NotImplementedException("Implement updateArtist method");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*public boolean artistExists(String name){
        try{
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM artists WHERE name = ?");
            ps.setString(1,name);
            ResultSet set = ps.executeQuery();
            if(set.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    public Artist getArtist(int id){
        try{
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM artists WHERE id = ?");
            ps.setInt(1,id);
            ResultSet set = ps.executeQuery();
            if (!set.next()){
                return null;
            }else{
                return new Artist(set.getInt(1), set.getString(2), set.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, Integer> getArtistsMap(){
        HashMap<String,Integer> artistsMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT artists.name, artists.id FROM artists")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    artistsMap.put(resultSet.getString(1),resultSet.getInt(2));
                }
            }
            return artistsMap;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Artist getArtist(String email) {
        try{
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM artists WHERE email = ?");
            ps.setString(1, email);
            ResultSet set = ps.executeQuery();
            if (!set.next()){
                return null;
            }else{
                return new Artist(set.getInt(1), set.getString(2), set.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to retrieve all Artists from the database.
     * @return ArrayList<Artist> Returns a list of all Artists.
     */
    public ArrayList<Artist> getArtists() {
        ArrayList<Artist> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM getAllArtists()")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    users.add(new Artist(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3)));
                }
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    // Series

    /**
     * This method is used insert a series name into the database.
     * @param series The series to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertSeries(Series series) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO series (name) VALUES (?)")) {
            ps.setString(1, series.getName());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to retrieve all series from the database.
     * @return Series Returns a series.
     */
    public Series getSeries(String name) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM series WHERE name = ?")) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next())
                    return new Series(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to retrieve all series from the database.
     * @return Series Returns a series.
     */
    public Series getSeriesBySeason(int seasonID) {
        int seriesID = getSeriesID(seasonID);
        return getSeries(seriesID);
    }

    /**
     * This method is used to retrieve all series from the database.
     * @return Series Returns a series.
     */
    public Series getSeries(int seriesID) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM series WHERE id = ?")) {
            ps.setInt(1, seriesID);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next())
                    return new Series(resultSet.getInt(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to retrieve all series from the database.
     * @return Series Returns a series.
     */
    public int getSeriesID(int seasonID) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM seasons WHERE id = ?")) {
            ps.setInt(1, seasonID);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next())
                    return resultSet.getInt(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * This method is used to retrieve all series from the database.
     * @return ArrayList<Series> Returns a list of all series.
     */
    public ArrayList<Series> getAllSeries() {
        ArrayList<Series> series = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM series")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Series s = new Series(resultSet.getInt(1), resultSet.getString(2));
                    series.add(s);
                }
            }
            return series;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getSeriesAndProductionID(){
        ArrayList<String> seriesAndProduction = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT * FROM getSeriesAndProductionID()");
            ResultSet set = ps.executeQuery();
            String result;
            while (set.next()){
               result = ""+set.getString(1)+","+set.getInt(2);
                seriesAndProduction.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seriesAndProduction;
    }

    // ProductionGenre

    /**
     * This method is used add a genre to an existing production in the database.
     * @param production The production to add the genre to
     * @param genreID The genre to add
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertProductionGenre(Production production, int genreID) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO productionGenres(productionID,genreID)+VALUES(?,?)")) {
            ps.setInt(1, production.getId());
            ps.setInt(2, genreID);
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    // Genre

    // TODO: Use a genre string instead of Production
    /**
     * This method is used insert a category to the database.
     * @param production The production to add the genre to
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertGenre(Production production) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO genres (name) VALUES (?)")) {
            ps.setArray(1, connection.createArrayOf("String",production.getGenres().toArray()));
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getGenres(int productionID){
        ArrayList<String> genres = new ArrayList<>();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT productionGenres.genreID FROM productionGenres " +
                    "WHERE productionGenres.productionID = ?");
            ps.setInt(1,productionID);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()) {
                PreparedStatement ps2 = connection.prepareStatement("SELECT name FROM genres WHERE id = ?");
                ps2.setInt(1,resultSet.getInt(1));
                ResultSet set =  ps2.executeQuery();
                set.next();
                genres.add(set.getString(1));
            }
            return genres;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }



    // Category

    /**
     * This method is used insert a category to the database.
     * @param production The production to add the genre to
     * @return int Returns the ID of the category.
     */
    public int insertCategory(Production production) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO categories (name) VALUES (?)")) {
            ps.setString(1,production.getCategory().toLowerCase());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getCategoryID(production);
    }

    /**
     * This method is used to retrieve the SuperUser with the given username, if the passwords match.
     * @param production The production
     * @return SuperUser Returns the SuperUser or null, if incorrect.
     */
    public int getCategoryID(Production production) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT getCategoryID(?)")) {
            ps.setString(1, production.getCategory().toLowerCase());
            try (ResultSet set = ps.executeQuery()) {
                if (set.next()) {
                    return set.getInt(1);
                } else {
                    return insertCategory(production);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * This method is used to retrieve the category with the given ID.
     * @param categoryID The ID of the category
     * @return String Returns the name of the category or null.
     */
    public String getCategoryID(int categoryID) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT categories.name FROM categories WHERE categories.id = ?")) {
            ps.setInt(1, categoryID);
            try (ResultSet set = ps.executeQuery()) {
                set.next();
                return set.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCategoryID(String name) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT categories.id FROM categories WHERE categories.name = ?")) {
            ps.setString(1, name);
            try (ResultSet set = ps.executeQuery()) {
                set.next();
                return set.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    // Season

    /**
     * This method is used insert a category to the database.
     * @param season The production to add the genre to
     * @return boolean Returns the ID of the category.
     */
    public boolean insertSeason(Season season) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO seasons (seasonNumber, seriesID) VALUES (?, ?)")) {
            ps.setInt(1, season.getSeasonNumber());
            ps.setInt(2, season.getSeriesID());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Season getSeason(int seasonNumber, int seriesID) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM seasons " +
                    "WHERE seasonNumber = ? AND seriesID = ?");
            ps.setInt(1, seasonNumber);
            ps.setInt(2, seriesID);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Season(resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to retrieve all series from the database.
     * @param seriesID The ID of the series
     * @return ArrayList<Season> Returns a list of all series.
     */
    public ArrayList<Season> getSeasons(int seriesID) {
        ArrayList<Season> series = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM seasons WHERE seriesID = ?")) {
            ps.setInt(1, seriesID);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Season s = new Season(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3));
                    series.add(s);
                }
            }
            return series;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSeasonNumber(int id){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT seasons.seasonNumber FROM seasons " +
                    "WHERE seasons.id = ?");
            ps.setInt(1,id);
            ResultSet set = ps.executeQuery();
            set.next();
            try{
                return set.getInt(1);
            }catch (Exception ignored){
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    // CastMember

    public ArrayList<CastMember> getCastMembers(int productionID) {
        ArrayList<CastMember> castMembers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getcastmembers(?)");
            preparedStatement.setInt(1,productionID);
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                castMembers.add(new CastMember(
                        set.getString(2),
                        set.getString(3),
                        set.getString(1),
                        productionID
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return castMembers;
    }

    public boolean insertCastMember(CastMember c){
        try {
            PreparedStatement ps = connection.prepareStatement("" +
                    "INSERT INTO castMembers(productionID, role, artistID) " +
                    "VALUES(?,?,?)");
            ps.setInt(1, c.getProductionID());
            ps.setString(2, c.getJobTitle());
            ps.setInt(3, c.getArtistID());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCastMember(CastMember c) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM castMembers " +
                    "WHERE productionID = ? AND role = ? AND artistID = ?");
            ps.setInt(1, c.getProductionID());
            ps.setString(2, c.getJobTitle());
            ps.setInt(3, c.getArtistID());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean chekIfCastMemberExists(CastMember castMember){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM castMembers WHERE castMembers.artistID = ?");
            ps.setInt(1, castMember.getArtistID());
            ResultSet set = ps.executeQuery();
            if(set.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(SuperUser user){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE superUsers SET  " +
                    "password = ? " +
                    "WHERE id = ?");
            ps.setString(1,user.getPassword());
            ps.setInt(2,user.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeUsername(SuperUser user){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE superUsers SET  " +
                    "username = ? " +
                    "WHERE id = ?");
            ps.setString(1,user.getUsername());
            ps.setInt(2,user.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeAdminStatus(SuperUser user){
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE superUsers SET  " +
                    "isAdmin = ? " +
                    "WHERE id = ?");
            ps.setBoolean(1,user.isSysAdmin());
            ps.setInt(2,user.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeEmail(Artist artist){
        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE artists SET  " +
                    "email = ?" +
                    "WHERE artists.id = ?");
            ps.setString(1, artist.getEmail());
            ps.setInt(2,artist.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeName(Artist artist){
        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE artists SET  " +
                    "name = ?" +
                    "WHERE artists.id = ?");
            ps.setString(1, artist.getName());
            ps.setInt(2,artist.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeCastMemberRole(CastMember castMember){
        try{
            PreparedStatement ps = connection.prepareStatement("UPDATE castMembers SET " +
                    "role = ? " +
                    "WHERE productionID = ?" +
                    "AND artistID = ?");
            ps.setString(1,castMember.getJobTitle());
            ps.setInt(2,castMember.getProductionID());
            ps.setInt(3,castMember.getArtistID());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
