package zerobase18.playticketing.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.global.type.ErrorCode;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.Theater;
import zerobase18.playticketing.theater.repository.SeatRepository;
import zerobase18.playticketing.theater.repository.TheaterRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;

    private Theater findTheater(int theaterId) {
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new CustomException(ErrorCode.THEATER_INVALID));
    }

    public List<Seat> createSeat(int theaterId, String[] seatTypeArr, char[] seatRowArr, int[] seatPriceArr, int[][] seatNumArr) {
        Theater theater = findTheater(theaterId);
        int[] seatTypeIdArr = new int[seatTypeArr.length];

        List<Seat> seatList = new ArrayList<>();

        for (int i = 0; i < seatTypeIdArr.length; i++) {

            // 시작번호부터 끝번호까지 좌석 생성
            int startNum = seatNumArr[0][i];
            int endNum = seatNumArr[1][i];
            for (int j = startNum; j <= endNum; j++) {

                // 이미 해당 극장에 생성된 좌석이라면 생성불가
                if (seatRepository.existsSeatBySeatNumAndSeatRow(j, seatRowArr[i])) {
                    List<Seat> foundSeats = seatRepository.findAllBySeatNumAndSeatRow(j, seatRowArr[i]);
                    int size = foundSeats.size();
                    for (int k = 0; k < size; k++) {
                        Seat foundSeat = foundSeats.get(k);
                        int foundTheaterId = foundSeat.getTheater().getId();
                        if (theaterId == foundTheaterId) {
                            throw new CustomException(ErrorCode.SEAT_CONFLICT);
                        }
                    }
                }

                Seat seat = seatRepository.save(Seat.builder()
                        .seatRow(seatRowArr[i])
                        .seatNum(j)
                        .seatType(seatTypeArr[i])
                        .seatPrice(seatPriceArr[i])
                        .theater(theater)
                        .build());

                // 생성된 좌석을 List 화 한다.
                seatList.add(seat);
            }

        }


        return seatList;
    }


}
