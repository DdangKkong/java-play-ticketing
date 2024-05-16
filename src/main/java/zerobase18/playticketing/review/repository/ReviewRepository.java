package zerobase18.playticketing.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>, CustomReviewRepository {

    boolean existsByReservationReserId(int reservationId);


}
