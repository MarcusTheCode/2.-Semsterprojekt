package data;

import domain.Production;
import domain.SuperUser;

import java.io.*;
import java.nio.Buffer;
import java.sql.*;

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
            FileReader fileReader = new FileReader("TXT/DatabaseCredentials");
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

    public boolean write(Production production){
        try {

            PreparedStatement ps = connection.prepareStatement("INSERT INTO productions(episodeNumber, \type,categoryID,seasonsID,producerID)");
            //ps.setString(1,);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public int getCategoryID(Production production){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT getCategoryID(?)");
            ps.setString(1,production.getCategory());
            ResultSet set = ps.executeQuery();
            return set.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

}
