package engine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity(name = "player")
@Table(name = "players")
public class User {
    @Id
    @Column(name = "email")
    @Email
    private String email;
    @Column(name = "password")
    @Size(min = 5)
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "enable")
    private boolean enable;

    public User() {}

    public User(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }
}
