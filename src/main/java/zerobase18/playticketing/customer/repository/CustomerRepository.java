package zerobase18.playticketing.customer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.customer.entity.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    boolean existsByLoginId(String loginId);

    Optional<Customer> findByLoginId(String loginId);

    List<Customer> findByLoginIdAndPassword(String loginId, String password);


}
