package zerobase18.playticketing.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.review.entity.Like;
import zerobase18.playticketing.review.entity.Review;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    Like findByCustomerAndReview(Customer customer, Review review);

    void deleteAllByReview(Review review);
}
