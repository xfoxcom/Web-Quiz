package engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Component
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @Column
    private long id;
    @NotEmpty
    @Column
    private String title;
    @NotEmpty
    @Column
    private String text;
    @Size(min = 2)
    @NotNull
    @Column
    private String[] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
    private int[] answer;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    public Quiz() {}

    public Quiz (long id, String title, String text, String[] options, int[] answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public long getId() {
        return id;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public void setText(String text) {
        this.text = text;
    }
}
