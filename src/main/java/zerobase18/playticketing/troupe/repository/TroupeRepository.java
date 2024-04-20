package zerobase18.playticketing.troupe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.seller.entity.Seller;
import zerobase18.playticketing.troupe.entity.Troupe;

import java.util.List;
import java.util.Optional;

public interface TroupeRepository extends JpaRepository<Troupe, Integer> {

    boolean existsByLoginId(String loginId);

    Optional<Troupe> findByLoginId(String loginId);


    List<Troupe> findByLoginIdAndPassword(String loginId, String password);
}
