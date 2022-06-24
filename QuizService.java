package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizPaging quizPaging;
    @Autowired
    private solvedQuizeRepository solvedQuizeRepository;

    public List<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize) {

        Pageable pageble = PageRequest.of(pageNo, pageSize);
        Page<Quiz> page = quizPaging.findAll(pageble);

        if (page.hasContent()) {
            return page.getContent();
        } else {
            return new ArrayList<Quiz>();
        }
    }

    Page<SolvedQuiz> allSolvedQ(String email, Pageable pageable) {
       return solvedQuizeRepository.allSolvedQByEmail(email, pageable);
    }

    @Query(value = "select * from solved_quizzes q where q.email = ?1", nativeQuery = true)
    public Page<Quiz> getSolved(String email,Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("dateTime").descending());
        return quizPaging.findAll(pageable);
    }
}
