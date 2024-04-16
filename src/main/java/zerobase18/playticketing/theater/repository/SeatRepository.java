package zerobase18.playticketing.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.Theater;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    boolean existsSeatBySeatNumAndSeatRow(int seatNum, char seatRow);
    List<Seat> findAllBySeatNumAndSeatRow(int seatNum, char seatRow);
    List<Seat> findAllByTheater(Theater theater);
    void deleteAllByTheater(Theater theater);
}
