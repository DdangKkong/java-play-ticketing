package zerobase18.playticketing.theater.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.theater.dto.CreateTheater;
import zerobase18.playticketing.theater.dto.ReadTheater;
import zerobase18.playticketing.theater.dto.TheaterDto;
import zerobase18.playticketing.theater.dto.UpdateTheater;
import zerobase18.playticketing.theater.service.TheaterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    // 극장 생성
    @PostMapping
    public ResponseEntity<CreateTheater.Response> createTheater(
            @RequestBody @Valid CreateTheater.Request request
    ) {
        TheaterDto theaterDto = theaterService.createTheater(request);
        CreateTheater.Response response = CreateTheater.Response.fromDto(theaterDto);

        return ResponseEntity.ok(response);
    }

    // 극장 조회
    @GetMapping
    public ResponseEntity<ReadTheater.Response> readTheater(
            @RequestParam(name = "theaterId") int theaterId
    ) {
        TheaterDto theaterDto = theaterService.readTheater(theaterId);
        ReadTheater.Response response = ReadTheater.Response.fromDto(theaterDto);

        return ResponseEntity.ok(response);
    }

    // 극장 수정
    @PutMapping
    public ResponseEntity<UpdateTheater.Response> updateTheater(
            @RequestBody @Valid UpdateTheater.Request request
    ){
        TheaterDto theaterDto = theaterService.updateTheater(request);
        UpdateTheater.Response response = UpdateTheater.Response.fromDto(theaterDto);

        return ResponseEntity.ok(response);
    }


}
