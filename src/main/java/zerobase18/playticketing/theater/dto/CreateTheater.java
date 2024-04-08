package zerobase18.playticketing.theater.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import zerobase18.playticketing.theater.entity.Seat;

import java.time.LocalDateTime;
import java.util.List;

public class CreateTheater {

    @Getter
    public static class Request {

        @NotNull
        private int sellerId;
        private String theaterName;
        private String theaterAdress;
        private int seatTotalCount;
        private int seatRowCount;
        // 아래 3개 행렬 모두 행렬의 크기가 seatType의 갯수이고 같다.
        private String[] seatTypeArr;
        private char[] seatRowArr;
        private int[] seatPriceArr;
        // 시작번호, 끝번호가 들어간 행렬 (크기 : i * 2)
        private int[][] seatNumArr;

    }

    @Builder
    @Getter
    public static class Response {

        private int theaterId;
        private int sellerId;
        private String theaterName;
        private String theaterAdress;
        private int seatTotalCount;
        private int seatRowCount;
        private List<Seat> seatList;
        private LocalDateTime createdAt;

        public static CreateTheater.Response fromDto(TheaterDto theaterDto) {
            return Response.builder()
                    .theaterId(theaterDto.getTheaterId())
                    .sellerId(theaterDto.getSellerId())
                    .theaterName(theaterDto.getTheaterName())
                    .theaterAdress(theaterDto.getTheaterAdress())
                    .seatTotalCount(theaterDto.getSeatTotalCount())
                    .seatRowCount(theaterDto.getSeatRowCount())
                    .createdAt(theaterDto.getCreatedAt())
                    .seatList(theaterDto.getSeatList())
                    .build();
        }

    }

}
