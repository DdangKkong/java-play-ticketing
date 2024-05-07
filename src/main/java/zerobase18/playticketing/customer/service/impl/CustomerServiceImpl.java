package zerobase18.playticketing.customer.service.impl;


import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.auth.dto.CustomerSignUpDto;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.customer.dto.SearchCustomer;
import zerobase18.playticketing.customer.dto.UpdateCustomerDto;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.customer.service.CustomerService;
import zerobase18.playticketing.global.exception.CustomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static zerobase18.playticketing.auth.type.UserState.*;
import static zerobase18.playticketing.global.type.ErrorCode.*;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplates;



    /**
     * 고객 회원 가입
     */
    @Override
    @Transactional
    public CustomerDto signUp(CustomerSignUpDto user) {

        boolean exists = customerRepository.existsByLoginId(user.getLoginId());

        if (exists) {
            throw new CustomException(ALREADY_USE_LOGIN_ID);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Customer customer = customerRepository.save(Customer.builder()
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .userType(UserType.CUSTOMER)
                .userState(ACTIVE)
                .name(user.getName())
                .birth(user.getBirth())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .build());
        return CustomerDto.fromEntity(customer);
    }

    /**
     * 고객 로그 아웃
     */

    public void logout(Integer customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if (redisTemplates.opsForValue().get("JWT_TOKEN:" + customer.getLoginId()) != null) {
            redisTemplates.delete("JWT_TOKEN:" + customer.getLoginId());
        }

    }


    /**
     * 고객 정보 수정
     */
    @Override
    @Transactional
    public CustomerDto updateCustomer(String loginId, String password, UpdateCustomerDto.Request request) {

        Customer customer = customerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        validateCustomer(customer);

        if (!passwordEncoder.matches(password, customer.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getEmail());

        Customer save = customerRepository.save(customer);

        return CustomerDto.fromEntity(save);
    }

    /**
     * 고객 정보 조회
     */
    @Override
    @Transactional
    public List<CustomerDto> searchCustomer(SearchCustomer.Request request) {


        Customer customer = customerRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        validateCustomer(customer);

        List<Customer> byLoginIdAndPassword = customerRepository.findByLoginIdAndPassword(customer.getLoginId(), customer.getPassword());



        return byLoginIdAndPassword.stream()
                .map(CustomerDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CustomerDto deleteCustomer(String loginId, String password) {

       Customer customer = customerRepository.findByLoginId(loginId)
               .orElseThrow(() -> new CustomException(USER_NOT_FOUND));




       if (!passwordEncoder.matches(password, customer.getPassword())) {
           throw new CustomException(PASSWORD_NOT_MATCH);
       }

       validateCustomer(customer);

       customer.setUserState(UserState.UN_REGISTERED);
       customer.setUnRegisteredAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

       customerRepository.save(customer);


        return CustomerDto.fromEntity(customer);
    }


    private void validateCustomer(Customer customer) {
        if (customer.getUserState().equals(UserState.UN_REGISTERED)) {
            throw new CustomException(UN_REGISTERED_USER);
        }
    }

}
