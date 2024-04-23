package zerobase18.playticketing.theater.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.theater.dto.*;
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

    // 극장 조회 - 누구나 가능
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

    // 극장 삭제 - deletedAt 만 넣어주고 나머지 데이터는 보관한다, 프론트에서 deletedAt 에 데이터가 있는것을 보고 안보이게 처리
    @DeleteMapping
    public ResponseEntity<DeleteTheater.Response> deleteTheater(
            @RequestParam(name = "theaterId") int theaterId,
            @RequestParam(name = "companyId") int companyId
    ){
        TheaterDto theaterDto = theaterService.deleteTheater(theaterId, companyId);
        DeleteTheater.Response response = DeleteTheater.Response.fromDto(theaterDto);

        return ResponseEntity.ok(response);
    }

}
