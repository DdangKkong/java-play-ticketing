package zerobase18.playticketing.play.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase18.playticketing.play.entity.ScheduleAndSeat;

@Repository
public interface ScheduleAndSeatRepository extends JpaRepository<ScheduleAndSeat, Integer> {
}
