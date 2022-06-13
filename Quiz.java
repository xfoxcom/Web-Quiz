package engine;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class Quiz {
    private int id;
   private String title;
   private String text;
   private String[] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
   private int answer;

    public Quiz() {}

    public Quiz (int id, String title, String text, String[] options, int answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
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

    public int getId() {
        return id;
    }

    public int getAnswer() {
        return answer;
    }
}
