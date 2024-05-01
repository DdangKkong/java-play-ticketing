package zerobase18.playticketing.qna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.customer.dto.UpdateCustomerDto;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.global.type.ErrorCode;
import zerobase18.playticketing.qna.dto.*;
import zerobase18.playticketing.qna.entity.Question;
import zerobase18.playticketing.qna.repository.QuestionRepository;
import zerobase18.playticketing.qna.service.AnswerService;
import zerobase18.playticketing.qna.service.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

import static zerobase18.playticketing.global.type.ErrorCode.QUESTION_NOT_FOUND;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/qna")
public class QuestionController {

    private final QuestionService questionService;

    private final QuestionRepository questionRepository;

    private final AnswerService answerService;


    /**
     * 질문 생성
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public CreateQuestion.Response response (
            @RequestBody CreateQuestion.Request request
    ) {
        QuestionDto question = questionService.createQuestion(request);

        return CreateQuestion.Response.fromEntity(question);
    }

    /**
     * 질문 전체 목록 조회
     */
    @GetMapping("/list")
    public ResponseEntity<Page<QuestionList>> searchQuestion(Pageable pageable) {

        Page<QuestionList> questionLists = questionService.searchQuestion(pageable);

        return ResponseEntity.ok(questionLists);

    }

    /**
     * 질문 상세 조회
     */
    @GetMapping("/detail")
    public List<QuestionDetail.Response> detailQuestion(
            @RequestBody @Valid QuestionDetail.Request request
    ) {

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new CustomException(QUESTION_NOT_FOUND));

        if (question.getAnswer() == null) {
            return questionService.detailQuestion(request)
                    .stream().map(
                            questionDto -> QuestionDetail.Response.builder()
                                    .name(questionDto.getName())
                                    .title(questionDto.getTitle())
                                    .content(questionDto.getContent())
                                    .questionState(questionDto.getQuestionState())
                                    .answer("답변 대기 중")
                                    .createdAt(questionDto.getCreatedAt())
                                    .build()
                    ).collect(Collectors.toList());
        }

        return questionService.detailQuestion(request)
                .stream().map(
                        questionDto -> QuestionDetail.Response.builder()
                                .name(questionDto.getName())
                                .title(questionDto.getTitle())
                                .content(questionDto.getContent())
                                .questionState(questionDto.getQuestionState())
                                .answer(question.getAnswer().getContent())
                                .createdAt(questionDto.getCreatedAt())
                                .build()
                ).collect(Collectors.toList());


    }

    /**
     * 질문 수정
     */
    @PutMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public QuestionUpdate.Response updateQuestion(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer questionId,
            @RequestBody @Valid QuestionUpdate.Request request
    ) {
        return QuestionUpdate.Response.fromEntity(
                questionService.updateQuestion(customerId, questionId, request)
        );
    }

    /**
     * 질문 삭제
     */
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public void deleteQuestion(
            @RequestParam @Valid Integer customerId,
            @RequestParam @Valid Integer questionId
    ) {
        questionService.deleteQuestion(customerId, questionId);
        log.info("질문 삭제 완료");

    }

}
