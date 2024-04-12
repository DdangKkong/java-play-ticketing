package zerobase18.playticketing.customer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import zerobase18.playticketing.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    boolean existsByLoginId(String loginId);


}
