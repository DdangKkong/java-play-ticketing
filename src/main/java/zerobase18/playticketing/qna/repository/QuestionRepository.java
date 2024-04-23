package zerobase18.playticketing.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase18.playticketing.qna.entity.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {


    Question findByAnswer_AnswerId(Integer answerId);

    List<Question> findByCustomerCustomerIdAndQuestionId(Integer customerId, Integer questionId);

}
