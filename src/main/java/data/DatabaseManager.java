package data;

import domain.*;

import java.io.*;
import java.lang.System;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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



    //region Production

    /**
     * This method is used insert a new production into the database.
     * @param production The production to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertProduction(Production production) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO productions(episodeNumber,type,categoryID,producerID,productionTitle,seasonID) " +
                "VALUES (?,?,?,?,?,?) " +
                "RETURNING id")) {
            if (production.getEpisodeNumber() != null)
                ps.setInt(1, production.getEpisodeNumber());
            else
                ps.setNull(1, Types.INTEGER);
            ps.setString(2, production.getType());
            ps.setInt(3, getCategory(production));
            ps.setLong(4, production.getOwnerID());
            ps.setString(5, production.getTitle());
            if (production.getSeasonID() != null)
                ps.setInt(6, production.getSeasonID());
            else
                ps.setNull(6, Types.INTEGER);

            // Change the ID of the production to this new one after insert
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                production.setID(resultSet.getInt(1));
            }

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
        }
        return false;
    }

    /**
     * This method is used to update a Production in the database.
     * @param production The Production to update
     */
    public boolean updateProduction(Production production) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE productions SET " +
                "episodeNumber = ?, " +
                "type = ?, " +
                "categoryID = ?, " +
                "producerID = ?, " +
                "productionTitle = ?, " +
                "seasonID = ? " +
                "WHERE id = ?; ")) {
            if (production.getEpisodeNumber() == null)
                ps.setNull(1, Types.INTEGER);
            else
                ps.setInt(1,production.getEpisodeNumber());
            ps.setString(2,production.getType());
            ps.setInt(3,production.getCategoryID());
            ps.setInt(4,production.getProducerID());
            ps.setString(5, production.getTitle());
            if (production.getSeasonID() == null)
                ps.setNull(6, Types.INTEGER);
            else
                ps.setInt(6,production.getSeasonID());
            ps.setInt(7, production.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
                        getCategory(resultSet.getInt(4)),
                        resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return production;
    }

    /**
     * This method is used to retrieve all productions that match the search pattern.
     * @param pattern The pattern to search for
     * @param searchForSeries Whether to search for series or title
     * @param producer The producer to search within
     * @return ArrayList<Production> Returns a list of matching productions.
     */
    public ArrayList<Production> getFilteredProductions(String pattern, boolean searchForSeries, SuperUser producer) {
        ArrayList<Production> productions = new ArrayList<>();
        String sqlCode;
        if (searchForSeries) {
            if (producer == null || producer.isSysAdmin())
                sqlCode = "SELECT * FROM getProductionsBySeries(?)";
            else
                sqlCode = "SELECT * FROM getProductionsBySeries(?,?)";
        } else {
            if (producer == null || producer.isSysAdmin())
                sqlCode = "SELECT * FROM getProductionsByTitle(?)";
            else
                sqlCode = "SELECT * FROM getProductionsByTitle(?,?)";
        }
        try (PreparedStatement ps = connection.prepareStatement(sqlCode)) {
            ps.setString(1, "%" + pattern.toLowerCase() + "%");
            if (producer != null && !producer.isSysAdmin())
                ps.setInt(2, producer.getId());
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Production p = new Production(
                            resultSet.getInt(2),
                            resultSet.getInt(5),
                            resultSet.getInt(6),
                            resultSet.getInt(1),
                            resultSet.getString(7),
                            getCategory(resultSet.getInt(4)),
                            resultSet.getString(3));
                    productions.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productions;
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
                            getCategory(resultSet.getInt(4)),
                            resultSet.getString(3));
                    productions.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productions;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productionMap;
    }

    //endregion

    //region SuperUser

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
            ps.execute();
            return true;
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
     * @param username the username of the SuperUser that should be edited
     * @param superUser what the SuperUser with the ID SuperUserID should be edited to
     */
    public boolean updateSuperUser(String username, SuperUser superUser) {
        HashMap<String, Integer> superUsersMap = getSuperUsersMap();
        Integer superUserID = superUsersMap.get(username);

        String sqlCode;
        sqlCode = "UPDATE superusers SET " +
                "username = ?, " +
                "password = ?, " +
                "isadmin = ? " +
                "WHERE id = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sqlCode)) {
            ps.setString(1,superUser.getUsername());
            ps.setString(2,superUser.getPassword());
            ps.setBoolean(3,superUser.isSysAdmin());
            ps.setInt(4,superUserID);

            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePassword(SuperUser user) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE superUsers SET  " +
                "password = ? " +
                "WHERE id = ?")) {;
            ps.setString(1,user.getPassword());
            ps.setInt(2,user.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeAdminStatus(SuperUser user) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE superUsers SET  " +
                "isAdmin = ? " +
                "WHERE id = ?")) {
            ps.setBoolean(1,user.isSysAdmin());
            ps.setInt(2,user.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
                if (resultSet.next())
                    return getSuperUser(resultSet.getInt(1));
                else
                    return null;
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
     * @param username The username of the SuperUser
     * @return SuperUser Returns the SuperUser with the username or null.
     */
    public SuperUser getSuperUser(String username) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM superUsers WHERE superUsers.username = ?")) {
            ps.setString(1, username);
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
        }
        return null;
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
        }
        return null;
    }

    /**
     * This method is used in the persistence tests to retrieve the ID in a non-hacky way
     * @return HashMap with production name as key and ID as value
     */
    public HashMap<String,Integer> getSuperUsersMap() {
        HashMap<String,Integer> superUsersMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT superusers.username, superusers.id FROM superusers")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    superUsersMap.put(resultSet.getString(1), resultSet.getInt(2));
            }
            return superUsersMap;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //endregion

    //region Artist

    /**
     * This method is used to insert a unique artist into the database.
     * @param artist The artist to insert into the database
     */
    public boolean insertArtist(Artist artist) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO artists(name,email)" +
                "VALUES (?,?)")) {
            ps.setString(1, artist.getName());
            ps.setString(2, artist.getEmail());
            ps.execute();
            return true;
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
            ps.execute();
            return true;
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
    public boolean editArtist(Artist artist) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE artists SET " +
                "email = ?, " +
                "name = ? " +
                "WHERE artists.id = ?")) {
            ps.setString(1, artist.getEmail());
            ps.setString(2,artist.getName());
            ps.setInt(3,artist.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editArtistName(Artist artist) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE artists SET " +
                    "name = ? " +
                    "WHERE artists.id = ?");
            ps.setString(1,artist.getName());
            ps.setInt(2,artist.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editArtistEmail(Artist artist) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE artists SET " +
                    "email = ? " +
                    "WHERE artists.id = ?");
            ps.setString(1,artist.getEmail());
            ps.setInt(2,artist.getId());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Artist getArtist(int id) {
        try (PreparedStatement ps = connection.prepareStatement("" +
                "SELECT * FROM artists WHERE id = ?")) {
            ps.setInt(1,id);
            ResultSet set = ps.executeQuery();
            if (set.next())
                return new Artist(set.getInt(1), set.getString(2), set.getString(3));
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, Integer> getArtistsMap() {
        HashMap<String,Integer> artistsMap = new HashMap<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT artists.name, artists.id FROM artists")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    artistsMap.put(resultSet.getString(1),resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artistsMap;
    }

    public Artist getArtist(String email) {
        try (PreparedStatement ps = connection.prepareStatement("" +
                "SELECT * FROM artists WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet set = ps.executeQuery();
            if (set.next())
                return new Artist(set.getInt(1), set.getString(2), set.getString(3));
            else
                return null;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    //endregion

    //region Series

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return series;
    }

    public ArrayList<String> getSeriesAndProductionID() {
        ArrayList<String> seriesAndProduction = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("" +
                "SELECT * FROM getSeriesAndProductionID()")) {
            ResultSet set = ps.executeQuery();
            String result;
            while (set.next()) {
                result = set.getString(1) + "," + set.getInt(2);
                seriesAndProduction.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seriesAndProduction;
    }

    //endregion

    //region Genre

    /**
     * This method is used add a genre to an existing production in the database.
     * @param production The production to add the genre to
     * @param genre The genre to add
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertGenre(Production production, Genre genre) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO productionGenres (productionID, genreID) VALUES (?,?)")) {
            ps.setInt(1, production.getId());
            ps.setInt(2, genre.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used add a genre to an existing production in the database.
     * @param production The production to add the genre to
     * @param genre The genre to add
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean deleteGenre(Production production, Genre genre) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM productionGenres "+
                "WHERE productionID = ? AND genreID = ?")) {
            ps.setInt(1, production.getId());
            ps.setInt(2, genre.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used retrieve all genres of a given production in the database.
     * @param productionID The ID of the production
     * @return ArrayList<Genre> Returns a list of this production's genres.
     */
    public ArrayList<Genre> getGenres(int productionID) {
        ArrayList<Genre> genres = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT id,name FROM productionGenres " +
                "INNER JOIN genres ON productiongenres.genreid = genres.id " +
                "WHERE productiongenres.productionid = ?;")) {
            ps.setInt(1, productionID);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    genres.add(new Genre(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public ArrayList<Genre> getAllGenres() {
        ArrayList<Genre> genres = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM genres")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    genres.add(new Genre(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    //endregion

    //region Category

    /**
     * This method is used insert a category to the database.
     * @deprecated Unused? Might work tho
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
        return getCategory(production);
    }

    /**
     * This method is used to retrieve the SuperUser with the given username, if the passwords match.
     * @param production The production
     * @return SuperUser Returns the SuperUser or null, if incorrect.
     */
    public int getCategory(Production production) {
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
    public String getCategory(int categoryID) {
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

    public ArrayList<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM categories");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                categories.add(resultSet.getString(2));
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //endregion

    //region Season

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

    /**
     * This method is used retrieve a season, given a series ID and season number.
     * @param seasonNumber The season number
     * @param seriesID The ID of the series this season belongs to
     * @return Season Returns the Season within the series.
     */
    public Season getSeason(int seasonNumber, int seriesID) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM seasons " +
                "WHERE seasonNumber = ? AND seriesID = ?")) {
            ps.setInt(1, seasonNumber);
            ps.setInt(2, seriesID);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return new Season(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3));
                }
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
        try (PreparedStatement ps = connection.prepareStatement("SELECT seasons.seasonNumber FROM seasons " +
                "WHERE seasons.id = ?")) {
            ps.setInt(1,id);
            try (ResultSet set = ps.executeQuery()) {
                if (set.next())
                    return set.getInt(1);
                else
                    return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //endregion

    //region CastMember

    public boolean insertCastMember(CastMember c){
        try (PreparedStatement ps = connection.prepareStatement("" +
                "INSERT INTO castMembers(productionID, role, artistID) " +
                "VALUES(?,?,?)")) {
            ps.setInt(1, c.getProductionID());
            ps.setString(2, c.getJobTitle());
            ps.setInt(3, c.getArtistID());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCastMember(CastMember castMember) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM castMembers " +
                "WHERE productionID = ? AND role = ? AND artistID = ?")) {
            ps.setInt(1, castMember.getProductionID());
            ps.setString(2, castMember.getJobTitle());
            ps.setInt(3, castMember.getArtistID());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeUsername(SuperUser user) {
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

    public boolean changeCastMemberRole(CastMember castMember) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE castMembers SET " +
                "role = ? " +
                "WHERE productionID = ?" +
                "AND artistID = ?")) {
            ps.setString(1,castMember.getJobTitle());
            ps.setInt(2,castMember.getProductionID());
            ps.setInt(3,castMember.getArtistID());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean castMemberExists(CastMember castMember){
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM castMembers " +
                "WHERE castMembers.artistID = ?")) {
            ps.setInt(1, castMember.getArtistID());
            try (ResultSet set = ps.executeQuery()) {
                return set.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<CastMember> getCastMembers(int productionID) {
        ArrayList<CastMember> castMembers = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM getcastmembers(?)")) {
            preparedStatement.setInt(1,productionID);
            try (ResultSet set = preparedStatement.executeQuery()) {
                while (set.next()) {
                    castMembers.add(new CastMember(
                            set.getString(2),
                            set.getString(3),
                            set.getString(1),
                            productionID
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return castMembers;
    }

    //endregion

}
