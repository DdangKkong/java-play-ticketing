package zerobase18.playticketing.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.company.entity.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByLoginId(String loginId);

    boolean existsByEmail(String email);

    Optional<Company> findByLoginId(String loginId);

    Optional<Company> findByEmail(String email);


    List<Company> findByLoginIdAndPassword(String loginId, String password);

}
