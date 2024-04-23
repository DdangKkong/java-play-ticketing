package zerobase18.playticketing.troupe.dto;


import lombok.*;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.troupe.entity.Troupe;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TroupeDto {

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


    public static TroupeDto fromEntity(Troupe troupe) {

        return TroupeDto.builder()
                .loginId(troupe.getLoginId())
                .password(troupe.getPassword())
                .userType(troupe.getUserType())
                .userState(troupe.getUserState())
                .name(troupe.getName())
                .company(troupe.getCompany())
                .phone(troupe.getPhone())
                .email(troupe.getEmail())
                .address(troupe.getAddress())
                .unRegisteredAt(troupe.getUnRegisteredAt())
                .build();
    }

}
