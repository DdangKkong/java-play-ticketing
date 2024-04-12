//package zerobase18.playticketing.theater.dto;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//import zerobase18.playticketing.theater.entity.Seat;
//
//@Builder
//@Getter
//@Setter
//public class SeatDto {
//
//    // 좌석 id
//    private int seatId;
//
//    // 극장 id
//    private int theaterId;
//
//    // 좌석 타입
//    private String  seatType;
//
//    // 좌석 가격
//    private int seatPrice;
//
//    // 좌석 열 (알파벳)
//    private char seatRow;
//
//    // 좌석 번호
//    private int seatNum;
//
//    public static SeatDto fromEntity(Seat seat) {
//        return SeatDto.builder()
//                .seatId(seat.getId())
//                .theaterId(seat.getTheater().getId())
//                .seatType(seat.getSeatType())
//                .seatPrice(seat.getSeatPrice())
//                .seatRow(seat.getSeatRow())
//                .seatNum(seat.getSeatNum())
//                .build();
//    }
//
//}
