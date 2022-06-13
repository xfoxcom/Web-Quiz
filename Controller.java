package engine;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
public class Controller {
    List<Quiz> ids = new LinkedList<>();
    @GetMapping("/api/quizzes")
    public List<Quiz> getQ () {
        return ids;
    }
    @GetMapping("/api/quizzes/{id}")
    public Quiz getQById(@PathVariable int id) {
        for (Quiz quiz : ids) {
            if (quiz.getId() == id) return quiz;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response postQ (@RequestBody Answer answer, @PathVariable int id) {
        for (Quiz quiz : ids) {
            if (quiz.getId() == id) {
                if (Arrays.compare(quiz.getAnswer(), answer.getAnswer()) == 0 ) {
                    return new Response(true, "Congratulations, you're right!");
                } else return new Response(false, "Wrong answer! Please, try again.");
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such Quiz");
    }
    @PostMapping("/api/quizzes")
    public Quiz createNewQ (@Valid @RequestBody Quiz q) {
        if (q.getAnswer() == null) {
            q.setAnswer(new int[]{});
        }
if (ids.isEmpty()) {
    ids.add(new Quiz(0, q.getTitle(), q.getText(), q.getOptions(), q.getAnswer()));
    return ids.get(0);
} else {
    ids.add(new Quiz(ids.size() + 1, q.getTitle(), q.getText(), q.getOptions(), q.getAnswer()));
    return ids.get(ids.size() - 1);
}
    }
}
