package zerobase18.playticketing.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase18.playticketing.theater.entity.Theater;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {
}
