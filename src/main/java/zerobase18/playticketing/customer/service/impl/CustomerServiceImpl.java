package zerobase18.playticketing.customer.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.auth.dto.SignUpDto;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.customer.service.CustomerService;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 고객 회원 가입
     */
    @Override
    @Transactional
    public CustomerDto signUp(SignUpDto user) {

        boolean exists = customerRepository.existsByLoginId(user.getLoginId());

        if (exists) {
            throw new RuntimeException("이미 존재하는 고객입니다.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Customer customer = customerRepository.save(Customer.builder()
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .userType(UserType.CUSTOMER)
                .name(user.getName())
                .birth(user.getBirth())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .createdAt(LocalDateTime.now())
                .build());

        return CustomerDto.fromEntity(customer);
    }
}
