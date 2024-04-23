package zerobase18.playticketing.admin.dto;


import lombok.*;
import zerobase18.playticketing.admin.entity.Admin;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.auth.type.UserType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    private String loginId;

    private String password;

    private UserState userState;

    private UserType userType;

    private String name;

    private String phone;

    private String email;

    private String address;

    private String unRegisteredAt;

    public static AdminDto fromEntity(Admin admin) {
        return AdminDto.builder()
                .loginId(admin.getLoginId())
                .password(admin.getPassword())
                .userState(admin.getUserState())
                .userType(admin.getUserType())
                .name(admin.getName())
                .phone(admin.getPhone())
                .email(admin.getEmail())
                .address(admin.getAddress())
                .unRegisteredAt(admin.getUnRegisteredAt())
                .build();
    }


}
