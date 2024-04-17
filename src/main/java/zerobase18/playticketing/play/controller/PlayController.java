package zerobase18.playticketing.play.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase18.playticketing.play.dto.CreatePlay;
import zerobase18.playticketing.play.dto.PlayDto;
import zerobase18.playticketing.play.service.PlayService;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theaters/plays")
public class PlayController {

    private final PlayService playService;

    @PostMapping
    public ResponseEntity<CreatePlay.Response> createPlay(
            @RequestBody @Valid CreatePlay.Request request
    ) throws ParseException {
        PlayDto playDto = playService.createPlay(request);
        CreatePlay.Response response = CreatePlay.Response.fromDto(playDto);
        return ResponseEntity.ok(response);
    }

}
