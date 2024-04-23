package zerobase18.playticketing.qna.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.customer.dto.UpdateCustomerDto;
import zerobase18.playticketing.qna.dto.*;

import java.util.List;

public interface QuestionService {


    /**
     * 질문 생성
     */
    QuestionDto createQuestion(CreateQuestion.Request request);

    /**
     * 질문 전체 목록 조회
     */
    Page<QuestionList> searchQuestion(Pageable pageable);

    /**
     * 질문 상세 조회
     */
    List<QuestionDto> detailQuestion(QuestionDetail.Request request);


    /**
     * 질문 수정
     */
    QuestionDto updateQuestion(Integer customerId, Integer questionId, QuestionUpdate.Request request);

    /**
     * 질문 삭제
     */
    void deleteQuestion(Integer customerId, Integer questionId);



}
