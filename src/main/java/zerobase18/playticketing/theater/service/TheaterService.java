package zerobase18.playticketing.theater.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.theater.dto.CreateTheater;
import zerobase18.playticketing.theater.dto.TheaterDto;
import zerobase18.playticketing.theater.dto.UpdateTheater;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.Seller;
import zerobase18.playticketing.theater.entity.Theater;
import zerobase18.playticketing.theater.repository.SeatRepository;
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
    private final SeatRepository seatRepository;

    private final SeatService seatService;

    private Seller findSeller(int sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("올바르지 않은 연극업체 정보입니다."));
    }

    private Theater findTheater(int theaterId) {
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("올바르지 않은 극장 정보입니다."));
    }

    // 극장 생성
    public TheaterDto createTheater(CreateTheater.Request request) {

        Seller seller = findSeller(request.getSellerId());

        // theater 미리 생성 (theaterId 값을 받아오기 위해)
        Theater theater = Theater.builder()
                        .theaterName(request.getTheaterName())
                        .theaterAdress(request.getTheaterAdress())
                        .seatTotalCount(request.getSeatTotalCount())
                        .seatRowCount(request.getSeatRowCount())
                        .seller(seller)
                        .build();
        theaterRepository.save(theater);

        // 좌석 타입 생성 -> 좌석 생성 -> 좌석 리스트 작성
        List<Seat> seatList = seatService.createSeat(
                theater.getId(), request.getSeatTypeArr(), request.getSeatRowArr(), request.getSeatPriceArr(), request.getSeatNumArr());

        // 생성 시간 넣기
        LocalDateTime now = LocalDateTime.now();
        // DateTimeFormatter를 사용하여 날짜와 시간 형식을 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        // 타입을 LocalDateTime 으로
        LocalDateTime createdAt = LocalDateTime.parse(formattedNow, formatter);

        // 다시 저장
        theater.setCreatedAt(createdAt);
        theaterRepository.save(theater);

        TheaterDto theaterDto = TheaterDto.fromEntity(theater);
        theaterDto.setSeatList(seatList);

        return theaterDto;
    }

    // 극장 조회 - 누구나 가능
    public TheaterDto readTheater(int theaterId) {

        // 극장 정보와 좌석 정보 불러오기
        Theater theater = findTheater(theaterId);
        List<Seat> seatList = seatRepository.findAllByTheater(theater);

        TheaterDto theaterDto = TheaterDto.fromEntity(theater);
        theaterDto.setSeatList(seatList);

        return theaterDto;
    }

    // 극장 수정
    @Transactional
    public TheaterDto updateTheater(UpdateTheater.Request request) {

        // 연극 업체 정보와 극장 정보 불러오기
        Seller seller = findSeller(request.getSellerId());
        Theater theater = findTheater(request.getTheaterId());

        // 좌석 정보를 제외한 모든 내용 업데이트
        theater.setTheaterName(request.getTheaterName());
        theater.setTheaterAdress(request.getTheaterAdress());
        theater.setSeatTotalCount(request.getSeatTotalCount());
        theater.setSeatRowCount(request.getSeatRowCount());

        // 수정 시간 넣기
        LocalDateTime now = LocalDateTime.now();
        // DateTimeFormatter를 사용하여 날짜와 시간 형식을 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        // 타입을 LocalDateTime 으로
        LocalDateTime updatedAt = LocalDateTime.parse(formattedNow, formatter);
        theater.setUpdatedAt(updatedAt);

        // 좌석 모두 삭제 후 다시 생성
        seatRepository.deleteAllByTheater(theater);
        List<Seat> seatList = seatService.createSeat(
                theater.getId(), request.getSeatTypeArr(), request.getSeatRowArr(), request.getSeatPriceArr(), request.getSeatNumArr());
        TheaterDto theaterDto = TheaterDto.fromEntity(theater);
        theaterDto.setSeatList(seatList);

        return theaterDto;
    }

    // 극장 삭제 - deletedAt 만 넣어주고 나머지 데이터는 보관한다, 프론트에서 deletedAt 에 데이터가 있는것을 보고 안보이게 처리
    @Transactional
    public TheaterDto deleteTheater(int theaterId, int sellerId) {

        // 연극 업체 정보와 극장 정보, 좌석 정보 불러오기
        Seller seller = findSeller(sellerId);
        Theater theater = findTheater(theaterId);
        List<Seat> seatList = seatRepository.findAllByTheater(theater);

        // 삭제 시간 넣기
        LocalDateTime now = LocalDateTime.now();
        // DateTimeFormatter를 사용하여 날짜와 시간 형식을 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        // 타입을 LocalDateTime 으로
        LocalDateTime deletedAt = LocalDateTime.parse(formattedNow, formatter);
        theater.setDeletedAt(deletedAt);

        TheaterDto theaterDto = TheaterDto.fromEntity(theater);
        theaterDto.setSeatList(seatList);

        return theaterDto;
    }

}
