package mylab.mail.util.emailblacklister.dao;

import mylab.mail.util.emailblacklister.model.EmailBlacklistForm;

import java.util.List;

/**
 * Created by kernel0237 on 10/22/14.
 */
public interface EmailBlacklistDAO {

    public void create(EmailBlacklistForm emailBlacklistForm);
    public void delete(Integer id);
    public EmailBlacklistForm get(String emailDomain);
    public List<EmailBlacklistForm> list();

}
