package mylab.mail.util.emailblacklister.services;

import mylab.mail.util.emailblacklister.dao.EmailBlacklistDAO;
import mylab.mail.util.emailblacklister.model.EmailBlacklistForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kernel0237 on 10/22/14.
 */
@Service
public class EmailBlacklistManager {

    @Autowired
    private EmailBlacklistDAO emailBlacklistDAO;

    @Transactional
    public void create(EmailBlacklistForm emailBlacklistForm) {
        emailBlacklistDAO.create(emailBlacklistForm);
    }

    @Transactional
    public void delete(String ids) {
        String _ids[] = ids.split(",");
        for (String id : _ids) emailBlacklistDAO.delete(Integer.parseInt(id));
    }

    public EmailBlacklistForm get(String emailDomain) { return emailBlacklistDAO.get(emailDomain); }

    public List<EmailBlacklistForm> list() { return emailBlacklistDAO.list(); }


}
