package engine;

import java.util.Arrays;
import java.util.List;

public class Answer {
    private int[] answer;
    public Answer () {}
    public Answer (int[] answer) {
        this.answer = answer;
    }
    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    /*public static void main(String[] args) {
        Answer answers = new Answer();
        answers.setAnswer(new int[]{});
        Quiz quiz = new Quiz(0, "f", "h", new String[]{"a", "c"}, new int[]{} );
        if (Arrays.compare(answers.getAnswer(),quiz.getAnswer()) == 0) {
            System.out.println("==");
        }
    }*/
}
