import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.Assert.*;

public class PersistenceTest {

    private static Connection connection;

    @BeforeClass
    public static void establishConnection(){
        connection = null;
        try {
            FileReader fileReader = new FileReader("src/main/resources/TXT/DatabaseCredentials");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String url = bufferedReader.readLine();
            Integer port = Integer.parseInt(bufferedReader.readLine());
            String databaseName = bufferedReader.readLine();
            String username = bufferedReader.readLine();
            String password = bufferedReader.readLine();
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectionTest(){
        assertNotNull(connection);
    }

    @Test
    public void migrationTest(){
        try {
            Reader reader = new BufferedReader(new FileReader("src/main/java/data/Migration.sql"));
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.setDelimiter("ENDFILE");
            scriptRunner.setLogWriter(null);    // Disables output when running the sql file
            scriptRunner.runScript(reader);
            scriptRunner.closeConnection();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @AfterClass
    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            assertTrue(false);
        }
    }
}
