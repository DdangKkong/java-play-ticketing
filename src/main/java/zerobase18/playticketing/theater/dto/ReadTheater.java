package zerobase18.playticketing.theater.dto;

import lombok.Builder;
import lombok.Getter;
import zerobase18.playticketing.theater.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;

public class ReadTheater {

    @Builder
    @Getter
    public static class Response {

        private int theaterId;
        private int sellerId;
        private String theaterName;
        private String theaterAdress;
        private int seatTotalCount;
        private int seatRowCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime deletedAt;
        private List<Seat> seatList;

        public static ReadTheater.Response fromDto(TheaterDto theaterDto) {
            return Response.builder()
                    .theaterId(theaterDto.getTheaterId())
                    .sellerId(theaterDto.getSellerId())
                    .theaterName(theaterDto.getTheaterName())
                    .theaterAdress(theaterDto.getTheaterAdress())
                    .seatTotalCount(theaterDto.getSeatTotalCount())
                    .seatRowCount(theaterDto.getSeatRowCount())
                    .createdAt(theaterDto.getCreatedAt())
                    .updatedAt(theaterDto.getUpdatedAt())
                    .deletedAt(theaterDto.getDeletedAt())
                    .seatList(theaterDto.getSeatList())
                    .build();
        }
    }

}
