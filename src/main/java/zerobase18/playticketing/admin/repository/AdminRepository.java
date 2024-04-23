package zerobase18.playticketing.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.admin.entity.Admin;

import java.util.List;
import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Integer> {


    boolean existsByLoginId(String loginId);

    Optional<Admin> findByLoginId(String loginId);

    List<Admin> findByLoginIdAndPassword(String loginId, String password);


}
