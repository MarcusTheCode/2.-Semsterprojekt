package data;

import domain.Production;

import java.io.*;
import java.nio.Buffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    // Structure of data for file credentials
    //url, port, databaseName, username, password){

    //Database variables
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "healthDB";
    private String username = "postgres";
    private String password = "";
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
            PreparedStatement ps = connection.prepareStatement("INSERT INTO productions(episodeNumber, type,categoryID,seasonsID,producerID)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
