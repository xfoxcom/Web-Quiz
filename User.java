package engine;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "player")
@Table(name = "players")
public class User {
    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "enable")
    private boolean enable = true;
    @OneToMany(mappedBy = "user")
    private List<Quiz> quizzes = new ArrayList<>();

    public User() {}

    public List<Quiz> getQuizzes() {
        return quizzes;
    }


    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

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
