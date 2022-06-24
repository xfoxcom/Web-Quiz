package engine;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solved_quizzes")
public class SolvedQuiz {
    private long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;
    @Id
    private String completedat;

    public SolvedQuiz() {}

    public String getCompletedAt() {
        return completedat;
    }

    public void setCompletedAt(String completedAt) {
        this.completedat = completedAt;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }


}
