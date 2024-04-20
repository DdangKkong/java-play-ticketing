package zerobase18.playticketing.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import zerobase18.playticketing.company.dto.CompanyDto;

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


    public SellerSignUpDto from(CompanyDto companyDto) {

        return SellerSignUpDto.builder()
                .loginId(companyDto.getLoginId())
                .password(companyDto.getPassword())
                .name(companyDto.getName())
                .company(companyDto.getCompany())
                .phone(companyDto.getPhone())
                .email(companyDto.getEmail())
                .address(companyDto.getAddress())
                .build();
    }

}
