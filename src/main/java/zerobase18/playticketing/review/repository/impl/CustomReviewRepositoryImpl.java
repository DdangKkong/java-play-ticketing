package zerobase18.playticketing.review.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import zerobase18.playticketing.review.entity.QReview;
import zerobase18.playticketing.review.entity.Review;
import zerobase18.playticketing.review.repository.CustomReviewRepository;

import java.util.List;

@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Slice<Review> searchByTitle(String title, Pageable pageable) {

        QReview review = QReview.review;

        List<Review> reviewList = jpaQueryFactory
                .selectFrom(review)
                .where(
                        review.title.contains(title)
                )
                .orderBy(
                        review.reviewId.desc(),
                        review.viewCount.desc(),
                        review.likeCount.desc()
                )
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return checkLastPage(pageable, reviewList);
    }



    /**
     * 무한 스크롤 방식을 처리하는 메서드
     */
    private Slice<Review> checkLastPage(Pageable pageable, List<Review> results) {

        boolean hasNext = false;

        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
