package zerobase18.playticketing.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.SeatType;
import zerobase18.playticketing.theater.repository.SeatRepository;
import zerobase18.playticketing.theater.repository.SeatTypeRepository;
import zerobase18.playticketing.theater.repository.TheaterRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatTypeRepository seatTypeRepository;
    private final SeatRepository seatRepository;
    private final TheaterRepository theaterRepository;

    private SeatType findSeatType(int seatTypeId) {
        return seatTypeRepository.findById(seatTypeId)
                .orElseThrow(() -> new RuntimeException("올바르지 않은 좌석 타입 정보입니다."));
    }

    public List<Seat> createSeat(int theaterId, int[] seatTypeIdArr, char[] seatRowArr, int[][] seatNumArr) {
        List<Seat> seatList = new ArrayList<>();

        for (int i = 0; i < seatTypeIdArr.length; i++) {
            SeatType seatType = findSeatType(seatTypeIdArr[i]);

            // 시작번호부터 끝번호까지 좌석 생성
            int startNum = seatNumArr[i][0];
            int endNum = seatNumArr[i][1];
            for (int j = startNum; j <= endNum; j++) {

                // 이미 해당 극장에 생성된 좌석이라면 생성불가
                if (seatRepository.existsSeatBySeatNumAndSeatRow(j, seatRowArr[i])) {
                    List<Seat> foundSeats = seatRepository.findAllBySeatNumAndSeatRow(j, seatRowArr[i]);
                    int size = foundSeats.size();
                    for (int k = 0; k < size; k++) {
                        Seat foundSeat = foundSeats.get(k);
                        int foundTheaterId = foundSeat.getSeatType().getTheater().getId();
                        if (theaterId == foundTheaterId) {
                            throw new RuntimeException("이미 생성된 좌석 정보입니다.");
                        }
                    }
                }

                Seat seat = seatRepository.save(Seat.builder()
                                .seatRow(seatRowArr[i])
                                .seatNum(j)
                                .seatType(seatType)
                                .build());

                // 생성된 좌석을 List 화 한다.
                seatList.add(seat);
            }

        }

        return seatList;

    }


}
