package zerobase18.playticketing.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.payment.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

}
