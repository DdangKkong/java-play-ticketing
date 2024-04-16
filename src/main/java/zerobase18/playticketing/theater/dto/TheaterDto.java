package zerobase18.playticketing.theater.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.Theater;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class TheaterDto {

    // 극장 id
    private int theaterId;

    // 연극업체 id
    private int sellerId;

    // 극장명
    private String theaterName;

    // 극장 주소
    private String theaterAdress;

    // 총 좌석 수
    private int seatTotalCount;

    // 좌석의 열 수 (가로)
    private int seatRowCount;

    // 생성 일시
    private LocalDateTime createdAt;

    // 수정 일시
    private LocalDateTime updatedAt;

    // 삭제 일시
    private LocalDateTime deletedAt;

    // 좌석 리스트
    private List<Seat> seatList;

    public static TheaterDto fromEntity(Theater theater) {
        return TheaterDto.builder()
                .theaterId(theater.getId())
                .sellerId(theater.getSeller().getId())
                .theaterName(theater.getTheaterName())
                .theaterAdress(theater.getTheaterAdress())
                .seatTotalCount(theater.getSeatTotalCount())
                .seatRowCount(theater.getSeatRowCount())
                .createdAt(theater.getCreatedAt())
                .updatedAt(theater.getUpdatedAt())
                .deletedAt(theater.getDeletedAt())
                .build();
    }

}
