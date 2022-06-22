package engine;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
public class Controller {
    private QuizeRepository quizeRepository;
    private UserRepository userRepository;
    public Controller (QuizeRepository quizeRepository, UserRepository userRepository) {
        this.quizeRepository = quizeRepository;
        this.userRepository = userRepository;
    }

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
    public Quiz createNewQ (@Valid @RequestBody Quiz q, Authentication auth) {
        String name = auth.getName();
        if (q.getAnswer() == null) {
            q.setAnswer(new int[]{});
        }
if (quizeRepository.count() == 0) {
    Quiz quiz = new Quiz(0, q.getTitle(), q.getText(), q.getOptions(), q.getAnswer());
    quiz.setUser(userRepository.findById(name).get());
    quizeRepository.save(quiz);
    userRepository.findById(name).get().getQuizzes().add(quiz); // TODO: 22.06.2022 added to list
    return quiz;
} else {
    Quiz quiz = new Quiz((int) quizeRepository.count(), q.getTitle(), q.getText(), q.getOptions(), q.getAnswer());
    quiz.setUser(userRepository.findById(name).get());
    quizeRepository.save(quiz);
    userRepository.findById(name).get().getQuizzes().add(quiz); // TODO: 22.06.2022 added to list
    return quiz;
}
    }
    @DeleteMapping("/api/quizzes/{id}")
    public void deleteQ (@PathVariable long id, Authentication auth) {
        String name = auth.getName();
        int count = 0;
        List<Quiz> list = userRepository.findById(name).get().getQuizzes();
        for (Quiz quiz : list) {
            if (quiz.getId() == id) {
                count++;
            }
        }

        if (quizeRepository.findById(id).isPresent() & count == 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if (count == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            for (Quiz quiz : list) {
                if (quiz.getId() == id) {
                    list.remove(quiz);
                    quizeRepository.deleteById(id);
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT);
                }
            }
        }
    }
}
