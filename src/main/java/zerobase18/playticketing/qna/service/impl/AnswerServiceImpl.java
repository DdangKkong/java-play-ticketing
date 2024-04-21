package zerobase18.playticketing.qna.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.admin.entity.Admin;
import zerobase18.playticketing.admin.repository.AdminRepository;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.qna.dto.AnswerDto;
import zerobase18.playticketing.qna.dto.CreateAnswer;
import zerobase18.playticketing.qna.entity.Answer;
import zerobase18.playticketing.qna.entity.Question;
import zerobase18.playticketing.qna.repository.AnswerRepository;
import zerobase18.playticketing.qna.repository.QuestionRepository;
import zerobase18.playticketing.qna.service.AnswerService;
import zerobase18.playticketing.qna.type.QuestionState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static zerobase18.playticketing.global.type.ErrorCode.*;
import static zerobase18.playticketing.qna.type.QuestionState.COMPLETE;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AdminRepository adminRepository;

    private final CustomerRepository customerRepository;

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;


    /**
     * 답변 생성
     */
    @Override
    @Transactional
    public AnswerDto createAnswer(Integer adminId, CreateAnswer.Request request) {

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new CustomException(ADMIN_NOT_FOUND));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new CustomException(QUESTION_NOT_FOUND));

        // 질문과 질문한 고객 아이디 비교 후 일치 하는지 확인
        if (!Objects.equals(customer.getCustomerId(), question.getCustomer().getCustomerId())) {
            throw new CustomException(QUESTION_NOT_MATCH);
        }

        // 이미 답변이 등록된 질문은 답변 등록 불가
        if (question.getQuestionState().equals(COMPLETE)) {
            throw new CustomException(ALREADY_ANSWER_QUESTION);
        }


        Answer save = answerRepository.save(
                Answer.builder()
                        .content(request.getContent())
                        .question(question)
                        .customer(customer)
                        .admin(admin)
                        .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                        .build()
        );
        question.setQuestionState(COMPLETE);
        question.setAnswer(save);

        return AnswerDto.fromEntity(save);
    }
}
