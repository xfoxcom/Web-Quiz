package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface solvedQuizeRepository extends PagingAndSortingRepository<SolvedQuiz, Long> {
    @Query(value = "select * from solved_quizzes q where q.email = ?1", nativeQuery = true)
  Page<SolvedQuiz> allSolvedQByEmail(String email, Pageable pageable);
}
