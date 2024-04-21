package zerobase18.playticketing.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import zerobase18.playticketing.admin.dto.AdminDto;

@Data
@Builder
@AllArgsConstructor
public class AdminSignUpDto {

    private String loginId;

    private String password;

    private String name;

    private String phone;

    private String email;

    private String address;


    public AdminSignUpDto fromEntity(AdminDto adminDto) {

        return AdminSignUpDto.builder()
                .loginId(adminDto.getLoginId())
                .password(adminDto.getPassword())
                .name(adminDto.getName())
                .phone(adminDto.getPhone())
                .email(adminDto.getEmail())
                .address(adminDto.getAddress())
                .build();
    }





}
