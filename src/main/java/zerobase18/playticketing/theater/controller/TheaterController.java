package zerobase18.playticketing.theater.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase18.playticketing.theater.dto.CreateTheater;
import zerobase18.playticketing.theater.dto.TheaterDto;
import zerobase18.playticketing.theater.service.TheaterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    // 극장 생성
    @PostMapping
    public ResponseEntity<CreateTheater.Response> createTheater(CreateTheater.Request request) {
        TheaterDto theaterDto = theaterService.createTheater(request);
        CreateTheater.Response response = CreateTheater.Response.fromDto(theaterDto);

        return ResponseEntity.ok(response);
    }

}
