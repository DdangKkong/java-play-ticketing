package zerobase18.playticketing.company.dto;


import lombok.*;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.company.entity.Company;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyDto {

    private String loginId;

    private String password;

    private UserType userType;

    private UserState userState;

    private String name;

    private String company;

    private String phone;

    private String email;

    private String address;

    private String unRegisteredAt;


    public static CompanyDto fromEntity(Company company) {

        return CompanyDto.builder()
                .loginId(company.getLoginId())
                .password(company.getPassword())
                .userState(company.getUserState())
                .userType(company.getUserType())
                .name(company.getName())
                .company(company.getCompany())
                .phone(company.getPhone())
                .email(company.getEmail())
                .address(company.getAddress())
                .unRegisteredAt(company.getUnRegisteredAt())
                .build();
    }

}
