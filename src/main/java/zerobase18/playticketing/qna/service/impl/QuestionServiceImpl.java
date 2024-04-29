package zerobase18.playticketing.qna.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.qna.dto.*;
import zerobase18.playticketing.qna.entity.Answer;
import zerobase18.playticketing.qna.entity.Question;
import zerobase18.playticketing.qna.repository.AnswerRepository;
import zerobase18.playticketing.qna.repository.QuestionRepository;
import zerobase18.playticketing.qna.service.QuestionService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static zerobase18.playticketing.global.type.ErrorCode.*;
import static zerobase18.playticketing.qna.type.QuestionState.REQUEST;


@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final CustomerRepository customerRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;


    /**
     * 질문 작성
     */
    @Override
    @Transactional
    public QuestionDto createQuestion(CreateQuestion.Request request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        Question save = questionRepository.save(Question.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .questionState(REQUEST)
                .answer(null)
                .customer(customer)
                .build());


        return QuestionDto.fromEntity(save);
    }

    /**
     * 질문 전체 목록 조회 페이징 처리(최신 순으로 정렬)
     */
    @Override
    @Transactional
    public Page<QuestionList> searchQuestion(Pageable pageable) {

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), 10,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Question> questions = questionRepository.findAll(sortedPageable);

        return QuestionList.questionLists(questions);
    }


    /**
     * 질문 상세 조회 - 답변이 있을 시 답변도 같이 조회
     */
    @Override
    @Transactional
    public List<QuestionDto> detailQuestion(QuestionDetail.Request request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new CustomException(QUESTION_NOT_FOUND));


        if (!Objects.equals(customer.getCustomerId(), question.getCustomer().getCustomerId())) {
            throw new CustomException(QUESTION_NOT_MATCH);
        }

        List<Question> questionList = questionRepository.findByCustomerCustomerIdAndQuestionId(customer.getCustomerId(), question.getQuestionId());

        return questionList.stream()
                .map(QuestionDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 질문 수정
     */
    @Override
    @Transactional
    public QuestionDto updateQuestion(Integer customerId, Integer questionId, QuestionUpdate.Request request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QUESTION_NOT_FOUND));

        if (!question.getCustomer().equals(customer)) {
            throw new CustomException(QUESTION_NOT_MATCH);
        }

        // 질문 수정 시 입력을 안하면 이전 제목과 내용이 저장.
        String title = Optional.ofNullable(request.getTitle()).orElse(question.getTitle());

        String content = Optional.ofNullable(request.getContent()).orElse(question.getContent());

        question.setTitle(title);
        question.setContent(content);

        //questionRepository.save(question);


        return QuestionDto.fromEntity(question);
    }

    /**
     * 질문 삭제(질문 삭제 시 답변도 같이 삭제된다.)
     */
    @Override
    @Transactional
    public void deleteQuestion(Integer customerId, Integer questionId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QUESTION_NOT_FOUND));


        if (!Objects.equals(customer.getCustomerId(), question.getCustomer().getCustomerId())) {
            throw new CustomException(QUESTION_NOT_MATCH);
        }

        Answer answer = answerRepository.findByCustomerCustomerIdAndQuestionQuestionId(customerId, questionId);

        if (answer.getContent().isEmpty()) {
            questionRepository.delete(question);
        } else {

            answerRepository.delete(answer);
            questionRepository.delete(question);
        }


    }
}
