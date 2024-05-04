package zerobase18.playticketing.qna.service;

import zerobase18.playticketing.qna.dto.*;

public interface AnswerService {

    /**
     * 답변 생성
     */
    AnswerDto createAnswer(Integer adminId, CreateAnswer.Request request);


    /**
     * 답변 수정
     */
    AnswerDto updateAnswer(Integer customerId, Integer questionId, AnswerUpdate.Request request);

}
