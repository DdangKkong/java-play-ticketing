package zerobase18.playticketing.seller.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.auth.dto.SellerSignUpDto;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.seller.dto.SellerDto;
import zerobase18.playticketing.seller.entity.Seller;
import zerobase18.playticketing.seller.repository.SellerRepository;
import zerobase18.playticketing.seller.service.SellerService;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    @Transactional
    public SellerDto signUp(SellerSignUpDto signUpDto) {

        boolean exists = sellerRepository.existsByLoginId(signUpDto.getLoginId());

        if (exists) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Seller seller = sellerRepository.save(Seller.builder()
                .loginId(signUpDto.getLoginId())
                .password(signUpDto.getPassword())
                .userType(UserType.SELLER)
                .name(signUpDto.getName())
                .company(signUpDto.getCompany())
                .phone(signUpDto.getPhone())
                .email(signUpDto.getEmail())
                .address(signUpDto.getAddress())
                .build());

        return SellerDto.fromEntity(seller);
    }
}
