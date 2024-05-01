package zerobase18.playticketing.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.review.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {



}
