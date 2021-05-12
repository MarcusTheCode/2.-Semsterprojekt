package data;

import domain.Production;
import domain.SuperUser;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

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
            FileReader fileReader = new FileReader("resources/TXT/DatabaseCredentials");
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

    // Insert methods

    /**
     * This method is used insert a new production into the database.
     * @param production The production to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertProduction(Production production) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO productions(episodeNumber,type,categoryID,seasonsID,producerID,productionTitle) " +
                "VALUES (?,?,?,?,?)")) {
            ps.setInt(1, production.getEpisodeNumber());
            ps.setString(2, production.getType());
            ps.setInt(3, getCategoryID(production));
            ps.setLong(4, production.getOwnerID());
            ps.setString(5, production.getTitle());
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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
     * This method is used insert a series name into the database.
     * @param name The series name to insert into the database
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean insertSeries(String name) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO series(name)+VALUES(?)")) {
            ps.setString(1, name);
            return ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO: Optimize method
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

    // Delete methods

    /**
     * This method is used to delete a SuperUser from the database, given an ID.
     * @param userID The ID of the SuperUser
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean deleteSuperUser(int userID) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM superUsers WHERE superUsers.id = ?")) {
            ps.setInt(1, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method is used to delete a Production from the database, given an ID.
     * @param productionID The ID of the Production
     * @return boolean Returns whether the execution succeeded.
     */
    public boolean deleteProduction(int productionID) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM productions WHERE productions.id = ?")) {
            ps.setInt(1, productionID);
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


    // Get methods

    /**
     * This method is used to retrieve a production from the database, given an ID.
     * @param productionID The ID of the production
     * @return Production Returns the production with the ID or null.
     */
    public Production getProduction(int productionID) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM productions WHERE productions.id = ?")) {
            ps.setInt(1, productionID);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                return new Production(
                        resultSet.getInt(2),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(1),
                        resultSet.getString(7),
                        getCategoryID(resultSet.getInt(4)),
                        resultSet.getString(3));
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
        }
        return null;
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
}
