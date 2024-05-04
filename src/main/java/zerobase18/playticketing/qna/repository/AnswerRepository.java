package zerobase18.playticketing.qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase18.playticketing.qna.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {


    Answer findByCustomerCustomerIdAndQuestionQuestionId(Integer customerId, Integer questionId);

}
