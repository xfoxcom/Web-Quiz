package engine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "auth")
public class Authorities {
    @Id
    private long id;
    @Column(name = "user_email")
    private String user_email;
    @Column(name = "auth")
    private String auth = "ROLE_USER";

    public Authorities() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getAuth() {
        return auth;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
