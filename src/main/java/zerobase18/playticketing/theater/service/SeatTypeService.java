package zerobase18.playticketing.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.SeatType;
import zerobase18.playticketing.theater.entity.Theater;
import zerobase18.playticketing.theater.repository.SeatTypeRepository;
import zerobase18.playticketing.theater.repository.TheaterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatTypeService {

    private final TheaterRepository theaterRepository;
    private final SeatTypeRepository seatTypeRepository;

    private final SeatService seatService;

    private Theater findTheater(int theaterId) {
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("올바르지 않은 극장 정보입니다."));
    }

    public List<Seat> createSeatType(int theaterId, String[] seatTypeArr, char[] seatRowArr, int[] seatPriceArr, int[][] seatNumArr) {
        Theater theater = findTheater(theaterId);
        int[] seatTypeIdArr = new int[seatTypeArr.length];

        for (int i = 0; i < seatTypeArr.length; i++) {
            String ST = seatTypeArr[i];
            int SP = seatPriceArr[i];

            // seatType 미리 생성 (seatTypeId 값을 받아오기 위해)
            SeatType seatType = seatTypeRepository.save(SeatType.builder()
                            .seatType(ST)
                            .seatPrice(SP)
                            .theater(theater)
                            .build());
            int seatTypeId = seatType.getId();
            // seatTypeId 를 행렬에 삽입
            seatTypeIdArr[i] = seatTypeId;
        }

        return seatService.createSeat(theaterId, seatTypeIdArr, seatRowArr, seatNumArr);
    }


}
