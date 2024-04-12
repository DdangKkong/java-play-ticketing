package zerobase18.playticketing.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
                .orElseThrow(() -> new RuntimeException("올바르지 않은 극장 정보입니다."));
    }

    public List<Seat> createSeat(int theaterId, String[] seatTypeArr, char[] seatRowArr, int[] seatPriceArr, int[][] seatNumArr) {
        Theater theater = findTheater(theaterId);
        int[] seatTypeIdArr = new int[seatTypeArr.length];

//        for (int i = 0; i < seatTypeArr.length; i++) {
//            String ST = seatTypeArr[i];
//            int SP = seatPriceArr[i];
//
//            // seatType 미리 생성 (seatTypeId 값을 받아오기 위해)
//            SeatType seatType = seatTypeRepository.save(SeatType.builder()
//                            .seatType(ST)
//                            .seatPrice(SP)
//                            .theater(theater)
//                            .build());
//            int seatTypeId = seatType.getId();
//            // seatTypeId 를 행렬에 삽입
//            seatTypeIdArr[i] = seatTypeId;
//        }


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
                            throw new RuntimeException("이미 생성된 좌석 정보입니다." + seatRowArr[i] + "열" + j + "번");
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
