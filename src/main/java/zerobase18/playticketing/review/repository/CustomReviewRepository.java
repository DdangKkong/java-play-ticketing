package zerobase18.playticketing.review.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerobase18.playticketing.review.entity.Review;

public interface CustomReviewRepository {

    Slice<Review> searchByTitle(String title, Pageable pageable);
}
