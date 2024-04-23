package zerobase18.playticketing.customer.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.auth.dto.CustomerSignUpDto;
import zerobase18.playticketing.auth.dto.SignInDto;
import zerobase18.playticketing.auth.security.TokenProvider;
import zerobase18.playticketing.auth.service.AuthService;
import zerobase18.playticketing.customer.dto.CustomerInfo;
import zerobase18.playticketing.customer.dto.DeleteCustomer;
import zerobase18.playticketing.customer.dto.SearchCustomer;
import zerobase18.playticketing.customer.dto.UpdateCustomerDto;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    /**
     * 고객 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> customerSignUp(@RequestBody @Valid CustomerSignUpDto customerSignUpDto) {

        return ResponseEntity.ok().body(
                customerSignUpDto.from(customerService.signUp(customerSignUpDto))
        );
    }

    /**
     * 고객 로그인
     */
    @PostMapping("/signin")
    public ResponseEntity<?> customerSignIn(@RequestBody @Valid SignInDto sign) {

        Customer customer = authService.authenticatedCustomer(sign);


        return ResponseEntity.ok(
                tokenProvider.createToken(
                        customer.getLoginId(),
                        customer.getUserType()
                )
        );
    }

    /**
     * 고객 정보 수정
     */
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public UpdateCustomerDto.Response updateCustomer(
            @RequestParam @Valid String loginId,
            @RequestParam @Valid String password,
            @RequestBody @Valid UpdateCustomerDto.Request request
    ) {
        return UpdateCustomerDto.Response.fromEntity(
                customerService.updateCustomer(loginId, password, request)
        );
    }

    /**
     * 고객 정보 조회 (List 형태로 반환)
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public List<CustomerInfo> SearchCustomer(
            @RequestBody @Valid SearchCustomer.Request request
            ) {

        return customerService.searchCustomer(request)
                .stream().map(
                        customerDto -> CustomerInfo.builder()
                                .name(customerDto.getName())
                                .birth(customerDto.getBirth())
                                .phone(customerDto.getPhone())
                                .email(customerDto.getEmail())
                                .address(customerDto.getAddress()).build()
                ).collect(Collectors.toList());
    }

    /**
     * 고객 회원 탈퇴
     */

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public DeleteCustomer.Response deleteCustomer(
            @RequestBody @Valid DeleteCustomer.Request request
    ) {
        return DeleteCustomer.Response.from(
                customerService.deleteCustomer(request.getLoginId(), request.getPassword())
        );
    }

}
