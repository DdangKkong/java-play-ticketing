package zerobase18.playticketing.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.theater.dto.CreateTheater;
import zerobase18.playticketing.theater.dto.TheaterDto;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.Seller;
import zerobase18.playticketing.theater.entity.Theater;
import zerobase18.playticketing.theater.repository.SellerRepository;
import zerobase18.playticketing.theater.repository.TheaterRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final SellerRepository sellerRepository;

    private final SeatTypeService seatTypeService;

    private Seller findSeller(int sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("올바르지 않은 연극업체 정보입니다."));
    }

    // 극장 생성
    public TheaterDto createTheater(CreateTheater.Request request) {

        Seller seller = findSeller(request.getSellerId());
        // theater 미리 생성 (theaterId 값을 받아오기 위해)
        Theater theater = theaterRepository.save(
                Theater.builder()
                        .theaterName(request.getTheaterName())
                        .theaterAdress(request.getTheaterAdress())
                        .seatTotalCount(request.getSeatTotalCount())
                        .seatRowCount(request.getSeatRowCount())
                        .seller(seller)
                        .build()
        );

        // 극장 생성 -> 좌석 타입 생성 -> 좌석 생성 -> 좌석 리스트 작성
        List<Seat> seatList = seatTypeService.createSeatType(
                theater.getId(), request.getSeatTypeArr(), request.getSeatRowArr(), request.getSeatPriceArr(), request.getSeatNumArr());

        // 생성 시간 넣기
        theater.setCreatedAt(LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        TheaterDto theaterDto = TheaterDto.fromEntity(theater);
        theaterDto.setSeatList(seatList);

        return theaterDto;
    }



}
