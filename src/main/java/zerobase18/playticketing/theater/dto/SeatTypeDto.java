package zerobase18.playticketing.theater.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.theater.entity.SeatType;

@Builder
@Getter
@Setter
public class SeatTypeDto {

    // 좌석 타입 id
    private int seatTypeId;

    // 극장 id
    private int theaterId;

    // 좌석 타입
    private String seatType;

    // 좌석 가격
    private int seatPrice;

    public static SeatTypeDto fromEntity(SeatType seatType) {
        return SeatTypeDto.builder()
                .seatTypeId(seatType.getId())
                .theaterId(seatType.getTheater().getId())
                .seatType(seatType.getSeatType())
                .seatPrice(seatType.getSeatPrice())
                .build();
    }

}
