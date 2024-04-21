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
import zerobase18.playticketing.admin.entity.Admin;
import zerobase18.playticketing.admin.repository.AdminRepository;
import zerobase18.playticketing.auth.dto.SignInDto;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.company.entity.Company;
import zerobase18.playticketing.company.repository.CompanyRepository;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.repository.CustomerRepository;
import zerobase18.playticketing.global.exception.CustomException;
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

    private final CompanyRepository companyRepository;

    private final TroupeRepository troupeRepository;

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;


    public Customer authenticatedCustomer(SignInDto sign) {
        Customer customer = checkCustomerLogInId(sign.getLoginId());

        validationState(customer.getUserState());

        validationPassword(sign.getPassword(), customer.getPassword());

        return customer;
    }

    public Company authenticatedCompany(SignInDto sign) {
        Company company = checkSellerLogInId(sign.getLoginId());


        validationState(company.getUserState());

        validationPassword(sign.getPassword(), company.getPassword());


        return company;
    }

    public Troupe authenticatedTroupe(SignInDto sign) {
        Troupe troupe = checkTroupeLogInId(sign.getLoginId());

        validationState(troupe.getUserState());


        validationPassword(sign.getPassword(), troupe.getPassword());

        return troupe;
    }

    public Admin authenticatedAdmin(SignInDto sign) {
        Admin admin = checkAdminLogInId(sign.getLoginId());

        validationState(admin.getUserState());


        validationPassword(sign.getPassword(), admin.getPassword());

        return admin;
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        if (customerRepository.existsByLoginId(loginId)) {

            Customer customer = checkCustomerLogInId(loginId);


            return createUserDetail(customer.getLoginId(), customer.getPassword(), CUSTOMER);

        } else if (companyRepository.existsByLoginId(loginId)) {

            Company company = checkSellerLogInId(loginId);

            return createUserDetail(company.getLoginId(), company.getPassword(), COMPANY);

        } else if (troupeRepository.existsByLoginId(loginId)) {

            Troupe troupe = checkTroupeLogInId(loginId);

            return createUserDetail(troupe.getLoginId(), troupe.getPassword(), TROUPE);

        } else if (adminRepository.existsByLoginId(loginId)) {

            Admin admin = checkAdminLogInId(loginId);

            return createUserDetail(admin.getLoginId(), admin.getPassword(), ADMIN);

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

    private Company checkSellerLogInId(String loginId) {
        return companyRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private Troupe checkTroupeLogInId(String loginId) {
        return troupeRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    private Admin checkAdminLogInId(String loginId) {
        return adminRepository.findByLoginId(loginId)
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
