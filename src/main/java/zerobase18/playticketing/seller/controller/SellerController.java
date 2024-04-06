package zerobase18.playticketing.seller.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase18.playticketing.auth.dto.SellerSignUpDto;
import zerobase18.playticketing.seller.service.SellerService;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<?> sellerSignUp(@RequestBody @Valid SellerSignUpDto signUpDto) {

        return ResponseEntity.ok().body(
                signUpDto.from(sellerService.signUp(signUpDto))
        );
    }

}
