package zerobase18.playticketing.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.seller.dto.SellerDto;

@Data
@Builder
@AllArgsConstructor
public class SellerSignUpDto {

    private String loginId;

    private String password;

    private String name;

    private String company;

    private String phone;

    private String email;

    private String address;


    public SellerSignUpDto from(SellerDto sellerDto) {

        return SellerSignUpDto.builder()
                .loginId(sellerDto.getLoginId())
                .password(sellerDto.getPassword())
                .name(sellerDto.getName())
                .company(sellerDto.getCompany())
                .phone(sellerDto.getPhone())
                .email(sellerDto.getEmail())
                .address(sellerDto.getAddress())
                .build();
    }

}
