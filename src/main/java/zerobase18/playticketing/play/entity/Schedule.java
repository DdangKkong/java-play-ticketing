package zerobase18.playticketing.play.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private int id;

    // 상영 날짜
    @Column(name = "schedule_date")
    private LocalDate scheduleDate;

    // 상영 시간
    @Column(name = "schedule_time")
    private LocalTime scheduleTime;

    // 연극 고유번호
    @ManyToOne
    @JoinColumn(name = "play_id")
    private Play play;

}
