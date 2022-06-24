package engine;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solved_quizzes")
public class SolvedQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String title;
    private String text;
    private String[] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;
    private LocalDateTime datetime;

    public SolvedQuiz() {}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.datetime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return datetime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String[] getOptions() {
        return options;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
