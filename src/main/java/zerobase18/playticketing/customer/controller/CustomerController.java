package zerobase18.playticketing.customer.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase18.playticketing.auth.dto.CustomerSignInDto;
import zerobase18.playticketing.auth.dto.CustomerSignUpDto;
import zerobase18.playticketing.auth.security.TokenProvider;
import zerobase18.playticketing.auth.service.AuthService;
import zerobase18.playticketing.customer.entity.Customer;
import zerobase18.playticketing.customer.service.CustomerService;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> customerSignUp(@RequestBody @Valid CustomerSignUpDto customerSignUpDto) {

        return ResponseEntity.ok().body(
                customerSignUpDto.from(customerService.signUp(customerSignUpDto))
        );
    }

    @PostMapping("/signin")
    public ResponseEntity<?> customerSignIn(@RequestBody @Valid CustomerSignInDto sign) {

        Customer customer = authService.authenticatedCustomer(sign);


        return ResponseEntity.ok(
                tokenProvider.createToken(
                        customer.getLoginId(),
                        customer.getUserType()
                )
        );
    }


}
