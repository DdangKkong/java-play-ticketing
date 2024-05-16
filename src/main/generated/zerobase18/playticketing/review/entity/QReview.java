package zerobase18.playticketing.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -1020155673L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final zerobase18.playticketing.global.entity.QBaseEntity _super = new zerobase18.playticketing.global.entity.QBaseEntity(this);

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final zerobase18.playticketing.customer.entity.QCustomer customer;

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final ListPath<Like, QLike> likes = this.<Like, QLike>createList("likes", Like.class, QLike.class, PathInits.DIRECT2);

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final zerobase18.playticketing.payment.entity.QReservation reservation;

    public final NumberPath<Integer> reviewId = createNumber("reviewId", Integer.class);

    public final StringPath title = createString("title");

    //inherited
    public final StringPath updatedAt = _super.updatedAt;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customer = inits.isInitialized("customer") ? new zerobase18.playticketing.customer.entity.QCustomer(forProperty("customer")) : null;
        this.reservation = inits.isInitialized("reservation") ? new zerobase18.playticketing.payment.entity.QReservation(forProperty("reservation"), inits.get("reservation")) : null;
    }

}

