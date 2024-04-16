package zerobase18.playticketing.seller.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase18.playticketing.auth.dto.SellerSignInDto;
import zerobase18.playticketing.auth.dto.SellerSignUpDto;
import zerobase18.playticketing.auth.security.TokenProvider;
import zerobase18.playticketing.auth.service.AuthService;
import zerobase18.playticketing.seller.entity.Seller;
import zerobase18.playticketing.seller.service.SellerService;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    private final TokenProvider tokenProvider;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> sellerSignUp(@RequestBody @Valid SellerSignUpDto signUpDto) {

        return ResponseEntity.ok().body(
                signUpDto.from(sellerService.signUp(signUpDto))
        );
    }

    @PostMapping("/signin")
    public ResponseEntity<?> sellerSignIn(@RequestBody @Valid SellerSignInDto sign) {

        Seller seller = authService.authenticatedSeller(sign);

        return ResponseEntity.ok(
                tokenProvider.createToken(
                        seller.getLoginId(),
                        seller.getUserType()
                )
        );
    }

}
