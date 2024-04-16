package zerobase18.playticketing.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

}
