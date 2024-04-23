package zerobase18.playticketing.play.entity;

import jakarta.persistence.*;
import lombok.*;
import zerobase18.playticketing.theater.entity.Theater;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Play {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "play_id")
    private int id;

    // 연극명
    @Column(name = "play_name")
    private String playName;

    // 연극 세부정보
    @Column(name = "play_deatails")
    private String playDetails;

    // 포스터 URL
    @Column(name = "poster_url")
    private String posterUrl;

    // 관람 등급
    @Column(name = "ratings")
    private String ratings;

    // 연극 장르
    @Column(name = "play_genre")
    private String playGenre;

    // 연극 시작일
    @Column(name = "play_start_date")
    private Date playStartDate;

    // 연극 종료일
    @Column(name = "play_end_date")
    private Date playEndDate;

    // 런타임 (관람시간)
    @Column(name = "runtime")
    private String runtime;

    // 출연진
    @Column(name = "actors")
    private String actors;

    // 연극 예약가능여부
    @Column(name = "reservationYN")
    private boolean reservationYN;

    // 작성 일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 수정 일시
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 삭제 일시
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 극장 고유번호
    @ManyToOne
    @JoinColumn(name = "theater_id")
    private Theater theater;

}
