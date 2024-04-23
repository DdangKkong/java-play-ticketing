package zerobase18.playticketing.play.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.play.dto.*;
import zerobase18.playticketing.play.service.PlayService;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theaters/plays")
public class PlayController {

    private final PlayService playService;

    // 연극, 연극스케줄, 연극스케줄 별 좌석 생성
    @PostMapping
    public ResponseEntity<CreatePlay.Response> createPlay(
            @RequestBody @Valid CreatePlay.Request request
    ) throws ParseException {
        PlayDto playDto = playService.createPlay(request);
        CreatePlay.Response response = CreatePlay.Response.fromDto(playDto);
        return ResponseEntity.ok(response);
    }

    // 연극, 연극스케줄, 연극스케줄 별 좌석 조회
    @GetMapping
    public ResponseEntity<ReadPlay.Response> readPlay(
            @RequestParam(name = "troupeId") int troupeId,
            @RequestParam(name = "playId") int playId
    ){
        PlayDto playDto = playService.readPlay(troupeId, playId);
        ReadPlay.Response response = ReadPlay.Response.fromDto(playDto);
        return ResponseEntity.ok(response);
    }

    // 연극, 연극스케줄, 연극스케줄 별 좌석 수정
    @PutMapping
    public ResponseEntity<UpdatePlay.Response> updatePlay(
            @RequestBody @Valid UpdatePlay.Request request
    ) throws ParseException {
        PlayDto playDto = playService.updatePlay(request);
        UpdatePlay.Response response = UpdatePlay.Response.fromDto(playDto);
        return ResponseEntity.ok(response);
    }

    // 연극, 연극스케줄, 연극스케줄 별 좌석 삭제
    @DeleteMapping
    public ResponseEntity<DeletePlay.Response> deletePlay(
            @RequestParam(name = "troupeId") int troupeId,
            @RequestParam(name = "playId") int playId
    ){
        PlayDto playDto = playService.deletePlay((troupeId), playId);
        DeletePlay.Response response = DeletePlay.Response.fromDto(playDto);
        return ResponseEntity.ok(response);
    }

}
