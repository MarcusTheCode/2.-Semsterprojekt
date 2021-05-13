import domain.DomainFacade;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DomainTest {

    @Test
    public void logInTest(){
        assertTrue(DomainFacade.login("admin","admin"));
    }

    @Test
    public void logOutTest(){
        assertTrue(DomainFacade.logout());
    }
}
