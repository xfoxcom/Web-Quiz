package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@RestController
public class Controller {
    @Autowired
    private solvedQuizeRepository solvedQuizeRepository;
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizeRepository quizeRepository;
    @Autowired
    private UserRepository userRepository;
    public Controller (QuizeRepository quizeRepository, UserRepository userRepository) {
        this.quizeRepository = quizeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/quizzes")
    public Page<Quiz> getQ (@RequestParam(defaultValue = "0") int page) {
        return quizService.getAllQuizzes(page, 10);
    }
    @GetMapping("/api/quizzes/{id}")
    public Quiz getQById(@PathVariable long id) {
        if (quizeRepository.existsById(id)) {
           return quizeRepository.findById(id).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/api/quizzes/{id}/solve")
    public Response postQ (@RequestBody Answer answer, @PathVariable long id, Authentication auth) {
        if (!quizeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such Quiz");
        }
        Quiz quiz = quizeRepository.findById(id).get();
        if(Arrays.compare(quizeRepository.findById(id).get().getAnswer(), answer.getAnswer()) == 0) {
            LocalDateTime time = LocalDateTime.now();
            SolvedQuiz solvedQuiz = new SolvedQuiz();
           // solvedQuiz.setDateTime(time);
            solvedQuiz.setCompletedAt(time.toString());
            solvedQuiz.setEmail(auth.getName());
            solvedQuiz.setId(quiz.getId());
            solvedQuizeRepository.save(solvedQuiz);
            return new Response(true, "Congratulations, you're right!");
        } else return new Response(false, "Wrong answer! Please, try again.");
    }
    @PostMapping("/api/quizzes")
    public Quiz createNewQ (@Valid @RequestBody Quiz q, Authentication auth) {
        String name = auth.getName();
        if (q.getAnswer() == null) {
            q.setAnswer(new int[]{});
        }

    Quiz quiz = new Quiz( q.getTitle(), q.getText(), q.getOptions(), q.getAnswer());
    quiz.setUser(userRepository.findById(name).get());
    quizeRepository.save(quiz);
    userRepository.findById(name).get().getQuizzes().add(quiz);
    return quiz;

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
    @GetMapping("/api/quizzes/completed")
    public Page<SolvedQuiz> getSolvedQ (Authentication auth, @RequestParam(defaultValue = "0") int page) { // TODO: 23.06.2022 with paging
        String name = auth.getName();

        Pageable sortedByDate = PageRequest.of(page, 10, Sort.by("completedAt").descending());

        return quizService.allSolvedQ(name, sortedByDate);


    }
}
