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
    private QuizeRepository quizeRepository;
    public Controller (QuizeRepository quizeRepository) {
        this.quizeRepository = quizeRepository;
    }
    List<Quiz> ids = new LinkedList<>();

    @GetMapping("/api/quizzes")
    public List<Quiz> getQ () {
        return (List<Quiz>) quizeRepository.findAll();
    }
    @GetMapping("/api/quizzes/{id}")
    public Quiz getQById(@PathVariable long id) {
        if (quizeRepository.existsById(id)) {
           return quizeRepository.findById(id).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response postQ (@RequestBody Answer answer, @PathVariable long id) {
        if (!quizeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such Quiz");
        }
        if(Arrays.compare(quizeRepository.findById(id).get().getAnswer(), answer.getAnswer()) == 0) {
            return new Response(true, "Congratulations, you're right!");
        } else return new Response(false, "Wrong answer! Please, try again.");
    }
    @PostMapping("/api/quizzes")
    public Quiz createNewQ (@Valid @RequestBody Quiz q) {
        if (q.getAnswer() == null) {
            q.setAnswer(new int[]{});
        }
if (quizeRepository.count() == 0) {
    Quiz quiz = new Quiz(0, q.getTitle(), q.getText(), q.getOptions(), q.getAnswer());
    ids.add(quiz);
    quizeRepository.save(quiz);
    return quiz;
} else {
    Quiz quiz = new Quiz((int) quizeRepository.count(), q.getTitle(), q.getText(), q.getOptions(), q.getAnswer());
    ids.add(quiz);
    quizeRepository.save(quiz);
    return quiz;
}
    }
}
