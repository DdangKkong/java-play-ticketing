package zerobase18.playticketing.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.admin.entity.Admin;

import java.util.List;
import java.util.Optional;


public interface AdminRepository extends JpaRepository<Admin, Integer> {


    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    Optional<Admin> findByLoginId(String loginId);

    Optional<Admin> findByEmail(String email);

    List<Admin> findByLoginIdAndPassword(String loginId, String password);


}
