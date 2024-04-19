package zerobase18.playticketing.play.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

public class DeletePlay {

    @Builder
    @Getter
    public static class Response {

        private int playId;
        private int theaterId;
        private String playName;
        private String playDetails;
        private String posterUrl;
        private String ratings;
        private String playGenre;
        private Date playStartDate;
        private Date playEndDate;
        private String runtime;
        private String actors;
        private boolean reservationYN;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime deletedAt;

        public static DeletePlay.Response fromDto(PlayDto playDto){
            return DeletePlay.Response.builder()
                    .playId(playDto.getPlayId())
                    .theaterId(playDto.getTheaterId())
                    .playName(playDto.getPlayName())
                    .playDetails(playDto.getPlayDetails())
                    .posterUrl(playDto.getPosterUrl())
                    .ratings(playDto.getRatings())
                    .playGenre(playDto.getPlayGenre())
                    .playStartDate(playDto.getPlayStartDate())
                    .playEndDate(playDto.getPlayEndDate())
                    .runtime(playDto.getRuntime())
                    .actors(playDto.getActors())
                    .reservationYN(playDto.isReservationYN())
                    .createdAt(playDto.getCreatedAt())
                    .updatedAt(playDto.getUpdatedAt())
                    .deletedAt(playDto.getDeletedAt())
                    .build();
        }
    }

}
