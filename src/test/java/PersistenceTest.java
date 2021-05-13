import domain.Production;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PersistenceTest {

    private Connection connection;
    private String url;
    private int port;
    private String databaseName;
    private String username;
    private String password;

    @Before
    public void establishConnection(){
        this.connection = null;
        try {
            FileReader fileReader = new FileReader("src/main/resources/TXT/DatabaseCredentials");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            url = bufferedReader.readLine();
            port = Integer.parseInt(bufferedReader.readLine());
            databaseName = bufferedReader.readLine();
            username = bufferedReader.readLine();
            password = bufferedReader.readLine();
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
            migrate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void migrate(){
        System.out.println("\nRunning Migration.sql file\n");
        try {
            Reader reader = new BufferedReader(new FileReader("src/main/java/data/Migration.sql"));
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectionTest(){
        assertNotNull(connection);
    }


}
