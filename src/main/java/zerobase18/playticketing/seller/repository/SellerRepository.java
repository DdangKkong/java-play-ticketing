package zerobase18.playticketing.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.seller.entity.Seller;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

    boolean existsByLoginId(String loginId);

    Optional<Seller> findByLoginId(String loginId);
}
