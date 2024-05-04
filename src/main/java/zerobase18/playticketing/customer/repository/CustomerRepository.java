package zerobase18.playticketing.customer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.customer.entity.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    boolean existsByLoginId(String loginId);

    Optional<Customer> findByLoginId(String loginId);

    Customer findByPassword(String password);

    List<Customer> findByLoginIdAndPassword(String loginId, String password);

    List<Customer> findByUpdatedAtBeforeAndUserStateEquals(String localDateTime, UserState status);




}
