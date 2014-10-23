package mylab.mail.util.emailblacklister;

import mylab.mail.util.emailblacklister.dao.EmailBlacklistDAO;
import mylab.mail.util.emailblacklister.model.EmailBlacklistForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by kernel0237 on 10/23/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/applicationContext-test.xml"})
public class EmailBlacklistManagerTest {

    @Autowired
    private EmailBlacklistDAO emailBlacklistDAO;

    @Test
    public void testGet() {
        EmailBlacklistForm result = emailBlacklistDAO.get("altavista.com");
        assertEquals(result.getEmailDomain(),"altavista.com");
    }

    @Test
    public void testList() {
        List<EmailBlacklistForm> results = emailBlacklistDAO.list();
        assertEquals(results.size(),5);
    }

}
