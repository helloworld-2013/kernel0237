package mylab.mail.util.emailblacklister.dao;

import mylab.mail.util.emailblacklister.model.EmailBlacklistForm;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by kernel0237 on 10/22/14.
 */
@Repository
public class EmailBlacklistDAOImpl implements EmailBlacklistDAO {

    @PersistenceContext(unitName="blacklistUnit")
    private EntityManager em;

    @Override
    public void create(EmailBlacklistForm emailBlacklistForm) {
        em.merge(emailBlacklistForm);
    }

    @Override
    public void delete(Integer id) {
        EmailBlacklistForm emailBlacklistForm = em.find(EmailBlacklistForm.class, id);
        em.remove(emailBlacklistForm);
    }

    @Override
    public EmailBlacklistForm get(String emailDomain) {
        Query query = em.createQuery("from EmailBlacklistForm where emailDomain = :emailDomain", EmailBlacklistForm.class);
        query.setParameter("emailDomain", emailDomain);
        List results = (List)query.getResultList();
        if (results==null || results.size()==0) return null;
        return (EmailBlacklistForm)results.get(0);
    }

    @Override
    public List<EmailBlacklistForm> list() {
        List<EmailBlacklistForm> results = em.createQuery("from EmailBlacklistForm").getResultList();
        return results;
    }
}
