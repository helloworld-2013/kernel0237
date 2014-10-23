package mylab.mail.util.emailblacklister.model;

import javax.persistence.*;

/**
 * Created by kernel0237 on 10/22/14.
 */
@Entity
@Table(name="blacklist_domains")
public class EmailBlacklistForm {

    @Id
    @SequenceGenerator(name="blacklistDomainsSeq", sequenceName="blacklist_domains_seq", allocationSize=1)
    @GeneratedValue(generator="blacklistDomainsSeq", strategy=GenerationType.SEQUENCE)
    @Column(name="id")
    private Integer id;

    @Column(name="email_domain")
    private String emailDomain;

    @Transient
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
