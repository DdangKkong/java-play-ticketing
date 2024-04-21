package zerobase18.playticketing.qna.service;

import zerobase18.playticketing.qna.dto.AnswerDto;
import zerobase18.playticketing.qna.dto.CreateAnswer;

public interface AnswerService {

    /**
     * 답변 생성
     */
    AnswerDto createAnswer(Integer adminId, CreateAnswer.Request request);

}
