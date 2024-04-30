package zerobase18.playticketing.qna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.qna.dto.AnswerUpdate;
import zerobase18.playticketing.qna.dto.CreateAnswer;
import zerobase18.playticketing.qna.service.AnswerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;


    @PostMapping
    public CreateAnswer.Response response(
            @RequestParam Integer adminId,
            @RequestBody CreateAnswer.Request request
    ) {

        return CreateAnswer.Response.fromEntity(
                answerService.createAnswer(adminId, request)
        );
    }


    /**
     * 답변 수정
     */
    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AnswerUpdate.Response updateAnswer(
            @RequestParam @Valid Integer adminId,
            @RequestParam @Valid Integer answerId,
            @RequestBody @Valid AnswerUpdate.Request request
    ) {
        return AnswerUpdate.Response.fromEntity(
                answerService.updateAnswer(adminId, answerId, request)
        );
    }
}
