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
import zerobase18.playticketing.auth.dto.TroupeSignInDto;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.seller.entity.Seller;
import zerobase18.playticketing.seller.repository.SellerRepository;
import zerobase18.playticketing.troupe.entity.Troupe;
import zerobase18.playticketing.troupe.repository.TroupeRepository;

import static zerobase18.playticketing.auth.type.UserState.UN_REGISTERED;
import static zerobase18.playticketing.auth.type.UserType.*;
import static zerobase18.playticketing.global.type.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final SellerRepository sellerRepository;

    private final TroupeRepository troupeRepository;

    private final PasswordEncoder passwordEncoder;


    public Customer authenticatedCustomer(CustomerSignInDto sign) {
        Customer customer = checkCustomerLogInId(sign.getLoginId());

        validationState(customer.getUserState());

        validationPassword(sign.getPassword(), customer.getPassword());

        return customer;
    }

    public Seller authenticatedSeller(SellerSignInDto sign) {
        Seller seller = checkSellerLogInId(sign.getLoginId());


        validationPassword(sign.getPassword(), seller.getPassword());


        return seller;
    }

    public Troupe authenticatedTroupe(TroupeSignInDto sign) {
        Troupe troupe = checkTroupeLogInId(sign.getLoginId());

        validationState(troupe.getUserState());


        validationPassword(sign.getPassword(), troupe.getPassword());

        return troupe;
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        if (customerRepository.existsByLoginId(loginId)) {

            Customer customer = checkCustomerLogInId(loginId);


            return createUserDetail(customer.getLoginId(), customer.getPassword(), CUSTOMER);

        } else if (sellerRepository.existsByLoginId(loginId)) {

            Seller seller = checkSellerLogInId(loginId);

            return createUserDetail(seller.getLoginId(), seller.getPassword(), SELLER);

        } else if (troupeRepository.existsByLoginId(loginId)) {

            Troupe troupe = checkTroupeLogInId(loginId);

            return createUserDetail(troupe.getLoginId(), troupe.getPassword(), TROUPE);

        }

        throw new UsernameNotFoundException("User not found with loginId" + loginId);
    }

    private UserDetails createUserDetail(String loginId, String password, UserType userType) {
        return User.withUsername(loginId)
                .password(passwordEncoder.encode(password))
                .roles(String.valueOf(userType))
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

    private Troupe checkTroupeLogInId(String loginId) {
        return troupeRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private void validationState(UserState state) {

        if (state.equals(UN_REGISTERED)) {
            throw new CustomException(UN_REGISTERED_USER);
        }
    }

    private void validationPassword(String password, String checkPassword) {
        if (!passwordEncoder.matches(password, checkPassword)) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }
    }
}
