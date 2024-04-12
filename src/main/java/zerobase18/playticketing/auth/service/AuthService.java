package zerobase18.playticketing.auth.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.auth.dto.CustomerSignInDto;
import zerobase18.playticketing.auth.dto.SellerSignInDto;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.seller.entity.Seller;
import zerobase18.playticketing.seller.repository.SellerRepository;

import static zerobase18.playticketing.global.type.ErrorCode.PASSWORD_NOT_MATCH;
import static zerobase18.playticketing.global.type.ErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final SellerRepository sellerRepository;

    private final PasswordEncoder passwordEncoder;


    public Customer authenticatedCustomer(CustomerSignInDto sign) {
        Customer customer = checkCustomerLogInId(sign.getLoginId());

        if (!passwordEncoder.matches(sign.getPassword(), customer.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }
        return customer;
    }

    public Seller authenticatedSeller(SellerSignInDto sign) {
        Seller seller = checkSellerLogInId(sign.getLoginId());

        if (!passwordEncoder.matches(sign.getPassword(), seller.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }
        return seller;
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        if (customerRepository.existsByLoginId(loginId)) {

            Customer customer = checkCustomerLogInId(loginId);

            return createCustomerDetail(customer.getLoginId(), customer.getPassword());

        }

        if (sellerRepository.existsByLoginId(loginId)) {

            Seller seller = checkSellerLogInId(loginId);

            return createSellerDetail(seller.getLoginId(), seller.getPassword());

        }

        throw new UsernameNotFoundException("User not found with loginId" + loginId);
    }

    private UserDetails createCustomerDetail(String loginId, String password) {
        return User.withUsername(loginId)
                .password(passwordEncoder.encode(password))
                .roles(String.valueOf(UserType.CUSTOMER))
                .build();
    }

    private UserDetails createSellerDetail(String loginId, String password) {
        return User.withUsername(loginId)
                .password(passwordEncoder.encode(password))
                .roles(String.valueOf(UserType.SELLER))
                .build();
    }


    private Customer checkCustomerLogInId(String loginId) {
        return customerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private Seller checkSellerLogInId(String loginId) {
        return sellerRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
