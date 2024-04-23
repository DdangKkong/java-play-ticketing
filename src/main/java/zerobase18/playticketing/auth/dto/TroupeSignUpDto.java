package zerobase18.playticketing.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import zerobase18.playticketing.troupe.dto.TroupeDto;

@Data
@Builder
@AllArgsConstructor
public class TroupeSignUpDto {

    private String loginId;

    private String password;

    private String name;

    private String company;

    private String phone;

    private String email;

    private String address;


    public TroupeSignUpDto from(TroupeDto troupeDto) {

        return TroupeSignUpDto.builder()
                .loginId(troupeDto.getLoginId())
                .password(troupeDto.getPassword())
                .name(troupeDto.getName())
                .company(troupeDto.getCompany())
                .phone(troupeDto.getPhone())
                .email(troupeDto.getEmail())
                .address(troupeDto.getAddress())
                .build();
    }

}
