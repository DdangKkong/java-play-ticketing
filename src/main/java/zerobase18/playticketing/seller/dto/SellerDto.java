package zerobase18.playticketing.seller.dto;


import lombok.*;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.seller.entity.Seller;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SellerDto {

    private String loginId;

    private String password;

    private UserType userType;

    private String name;

    private String company;

    private String phone;

    private String email;

    private String address;


    public static SellerDto fromEntity(Seller seller) {

        return SellerDto.builder()
                .loginId(seller.getLoginId())
                .password(seller.getPassword())
                .userType(seller.getUserType())
                .name(seller.getName())
                .company(seller.getCompany())
                .phone(seller.getPhone())
                .email(seller.getEmail())
                .address(seller.getAddress())
                .build();
    }

}
