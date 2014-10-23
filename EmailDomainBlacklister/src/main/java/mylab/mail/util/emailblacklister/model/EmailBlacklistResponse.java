package mylab.mail.util.emailblacklister.model;

import java.util.List;

/**
 * Created by kernel0237 on 10/22/14.
 */
public class EmailBlacklistResponse {

    private EmailBlacklistForm emailBlacklistForm;
    private List<EmailBlacklistForm> emailBlacklistForms;

    public EmailBlacklistForm getEmailBlacklistForm() {
        return emailBlacklistForm;
    }

    public void setEmailBlacklistForm(EmailBlacklistForm emailBlacklistForm) {
        this.emailBlacklistForm = emailBlacklistForm;
    }

    public List<EmailBlacklistForm> getEmailBlacklistForms() {
        return emailBlacklistForms;
    }

    public void setEmailBlacklistForms(List<EmailBlacklistForm> emailBlacklistForms) {
        this.emailBlacklistForms = emailBlacklistForms;
    }
}
