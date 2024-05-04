package zerobase18.playticketing.play.dto;

import lombok.Builder;
import lombok.Getter;
import zerobase18.playticketing.global.type.PlayGenre;
import zerobase18.playticketing.global.type.Ratings;
import zerobase18.playticketing.global.type.ReservationYN;
import zerobase18.playticketing.play.entity.Schedule;
import zerobase18.playticketing.play.entity.ScheduleSeat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ReadPlay {

    @Builder
    @Getter
    public static class Response {

        private int playId;
        private int theaterId;
        private int troupeId;
        private String playName;
        private String playDetails;
        private String posterUrl;
        private Ratings ratings;
        private PlayGenre playGenre;
        private Date playStartDate;
        private Date playEndDate;
        private String runtime;
        private String actors;
        private ReservationYN reservationYN;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime deletedAt;
        private List<Schedule> scheduleList;
        private List<ScheduleSeat> scheduleSeats;

        public static ReadPlay.Response fromDto(PlayDto playDto){
            return ReadPlay.Response.builder()
                    .playId(playDto.getPlayId())
                    .theaterId(playDto.getTheaterId())
                    .troupeId(playDto.getTroupeId())
                    .playName(playDto.getPlayName())
                    .playDetails(playDto.getPlayDetails())
                    .posterUrl(playDto.getPosterUrl())
                    .ratings(playDto.getRatings())
                    .playGenre(playDto.getPlayGenre())
                    .playStartDate(playDto.getPlayStartDate())
                    .playEndDate(playDto.getPlayEndDate())
                    .runtime(playDto.getRuntime())
                    .actors(playDto.getActors())
                    .reservationYN(playDto.getReservationYN())
                    .createdAt(playDto.getCreatedAt())
                    .updatedAt(playDto.getUpdatedAt())
                    .deletedAt(playDto.getDeletedAt())
                    .scheduleList(playDto.getScheduleList())
                    .scheduleSeats(playDto.getScheduleSeats())
                    .build();
        }
    }

}
