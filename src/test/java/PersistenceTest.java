import data.DataFacade;
import domain.CastMember;
import domain.DomainFacade;
import domain.Production;
import domain.SuperUser;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.*;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PersistenceTest {

    /**
     * Note: when debugging the tests, its sometimes needed to run
     * the tests again without debugging, to 'clean' the database.
     * I don't know why this happens.
     */

    private static Connection connection;
    private static ScriptRunner scriptRunner;
    private static Reader reader;
    private static String url;
    private static Integer port;
    private static String databaseName;
    private static String username;
    private static String password;

    /**
     * This method runs once before all the tests
     * it reads the DatabaseCredentials file and connects
     * to the database
     */
    @BeforeClass
    public static void setUp(){
        connection = null;
        try {
            // Load DatabaseCredentials
            FileReader fileReader = new FileReader("src/main/resources/TXT/DatabaseCredentials");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            url = bufferedReader.readLine();
            port = Integer.parseInt(bufferedReader.readLine());
            databaseName = bufferedReader.readLine();
            username = bufferedReader.readLine();
            password = bufferedReader.readLine();

            // Register driver and connect to database
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);

            // Load the migration script
            reader = new BufferedReader(new FileReader("src/main/java/data/Migration.sql"));
            scriptRunner = new ScriptRunner(connection);
            scriptRunner.setDelimiter("ENDFILE");
            scriptRunner.setLogWriter(null);    // Disables output when running the sql file
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
        scriptRunner.runScript(reader);
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
        DataFacade.insertProduction(production);

        HashMap<String, Integer> productionsMap = DataFacade.getProductionsMap();
        Integer productionID = productionsMap.get(production.getTitle());

        production = DataFacade.getProduction(productionID);
        assertNotNull(production);
    }

    @Test
    public void deleteProductionTest() {
        Production production = new Production("Shrek", 2, "documentary", 1, "movie");
        DataFacade.insertProduction(production);

        HashMap<String, Integer> productionsMap = DataFacade.getProductionsMap();
        Integer productionID = productionsMap.get(production.getTitle());

        Production tempProduction = DataFacade.getProduction(productionID);
        assertNotNull(tempProduction);

        DataFacade.deleteProduction(productionID);
        productionsMap = DataFacade.getProductionsMap();

        productionID = productionsMap.get(production.getTitle());
        assertNull(productionID);
    }

    @Test
    public void saveSuperUserTest() {
        HashMap<String, Integer> superUsersMap = DataFacade.getSuperUsersMap();
        SuperUser superUser = new SuperUser("Thomas Vinterberg","druk123",false);

        Integer superUserID = superUsersMap.get(superUser.getUsername());
        assertNull(superUserID);

        DataFacade.insertSuperUser(superUser);
        superUsersMap = DataFacade.getSuperUsersMap();
        superUserID = superUsersMap.get(superUser.getUsername());

        superUser = DataFacade.getSuperUser(superUserID);
        assertNotNull(superUser);
    }

    @Test
    public void deleteSuperUserTest() {
        SuperUser superUser = new SuperUser("Steven Spielberg","1218",false);
        DataFacade.insertSuperUser(superUser);

        HashMap<String, Integer> superUsersMap = DataFacade.getSuperUsersMap();
        Integer superUserID = superUsersMap.get(superUser.getUsername());

        SuperUser tempSuperUser = DataFacade.getSuperUser(superUserID);
        assertNotNull(tempSuperUser);

        DataFacade.deleteSuperUser(superUserID);
        superUsersMap = DataFacade.getProductionsMap();

        superUserID = superUsersMap.get(superUser.getUsername());
        assertNull(superUserID);
    }


    // TODO: Edit SuperUser test

    @AfterClass
    public static void tearDown(){
        try {
            scriptRunner.closeConnection();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}
