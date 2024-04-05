package zerobase18.playticketing.customer.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase18.playticketing.auth.dto.SignUpDto;
import zerobase18.playticketing.customer.service.CustomerService;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<?> customerSignUp(@RequestBody @Valid SignUpDto signUpDto) {

        return ResponseEntity.ok().body(
                signUpDto.from(customerService.signUp(signUpDto))
        );
    }


}
