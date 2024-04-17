package zerobase18.playticketing.play.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.play.entity.Play;
import zerobase18.playticketing.play.entity.Schedule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class PlayDto {

    // 연극 고유번호
    private int playId;

    // 극장 고유번호
    private int theaterId;

    // 연극명
    private String playName;

    // 연극 세부정보
    private String playDetails;

    // 포스터 URL
    private String posterUrl;

    // 관람 등급
    private String ratings;

    // 연극 장르
    private String playGenre;

    // 연극 시작일
    private Date playStartDate;

    // 연극 종료일
    private Date playEndDate;

    // 런타임 (관람시간)
    private String runtime;

    // 출연진
    private String actors;

    // 연극 예약가능여부
    private boolean reservationYN;

    // 작성 일시
    private LocalDateTime createdAt;

    // 수정 일시
    private LocalDateTime updatedAt;

    // 삭제 일시
    private LocalDateTime deletedAt;

    // 연극 스케줄 리스트
    private List<Schedule> scheduleList;

    // 연극 스케줄별 좌석 여부
    private boolean scheduleSeatYN;
    public static PlayDto fromEntity(Play play){
        return PlayDto.builder()
                .playId(play.getId())
                .theaterId(play.getTheater().getId())
                .playName(play.getPlayName())
                .playDetails(play.getPlayDetails())
                .posterUrl(play.getPosterUrl())
                .ratings(play.getRatings())
                .playGenre(play.getPlayGenre())
                .playStartDate(play.getPlayStartDate())
                .playEndDate(play.getPlayEndDate())
                .runtime(play.getRuntime())
                .actors(play.getActors())
                .reservationYN(play.isReservationYN())
                .createdAt(play.getCreatedAt())
                .updatedAt(play.getUpdatedAt())
                .deletedAt(play.getDeletedAt())
                .build();
    }

}
