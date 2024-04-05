package zerobase18.playticketing.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import zerobase18.playticketing.customer.dto.CustomerDto;

@Data
@Builder
@AllArgsConstructor
public class SignUpDto {

    private String loginId;

    private String password;

    private String name;

    private String birth;

    private String phone;

    private String email;

    private String address;


    public SignUpDto from(CustomerDto customerDto) {

        return SignUpDto.builder()
                .loginId(customerDto.getLoginId())
                .password(customerDto.getPassword())
                .name(customerDto.getName())
                .birth(customerDto.getBirth())
                .phone(customerDto.getPhone())
                .email(customerDto.getEmail())
                .address(customerDto.getAddress())
                .build();
    }

}
