import data.DataFacade;
import domain.CastMember;
import domain.Production;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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

    @Test
    public void getCreditTest(){
        TestCastMember expectedCastMember = new TestCastMember("Barry B. Benson","BarryBeeBenson@bee.hive","Actor",5);

        Production production = DataFacade.getProduction(5);
        TestCastMember castMember = new TestCastMember(production.getCastMembers().get(0));

        assertTrue(castMember.equals(expectedCastMember));
    }

    @Test
    public void saveProductionTest(){
        Production production = new Production("Shrek",2,"documentary",1,"movie");
        assertTrue(DataFacade.insertProduction(production));
    }

    @Test
    public void deleteProductionTest() {
        DataFacade.insertProduction(new Production("Shrek", 2, "documentary", 1, "movie"));
        Production production1 = DataFacade.getProduction(6);
        assertNotNull(production1);

        DataFacade.deleteProduction(6);
        Production production2 = DataFacade.getProduction(6);
        assertNull(production2);
    }

    // TODO: Save SuperUser test

    // TODO: Delete Superuser test

    // TODO: Edit SuperUser test

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
