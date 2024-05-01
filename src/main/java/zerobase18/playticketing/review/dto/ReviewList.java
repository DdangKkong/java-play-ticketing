package zerobase18.playticketing.review.dto;


import lombok.*;
import org.springframework.data.domain.Page;
import zerobase18.playticketing.review.entity.Review;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewList {

    private String title;

    private String playName;

    private Integer rating;

    private Integer commentCount;

    private Integer likeCount;

    private Long viewCount;

    private String createdAt;


    public static Page<ReviewList> ReviewLists(Page<Review> reviews) {
        return reviews.map(review -> ReviewList.builder()
                .title(review.getTitle())
                .playName(review.getReservation().getReserPlayName())
                .rating(review.getRating())
                .commentCount(review.updateCommentCount())
                .viewCount(review.getViewCount())
                .likeCount(review.getLikeCount())
                .createdAt(review.getCreatedAt())
                .build());
    }

}
