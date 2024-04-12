package zerobase18.playticketing.theater.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase18.playticketing.theater.entity.Seller;
import zerobase18.playticketing.theater.repository.SellerRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private final SellerRepository sellerRepository;

    // 극장 생성
    @PostMapping
    public ResponseEntity<?> createTheater(@RequestBody @Valid String sellerName) {
        Seller seller = Seller.builder().sellerName(sellerName).build();
        sellerRepository.save(seller);

        return ResponseEntity.ok(seller);
    }

}
