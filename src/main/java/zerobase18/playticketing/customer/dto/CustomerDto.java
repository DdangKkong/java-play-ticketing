package zerobase18.playticketing.customer.dto;

import lombok.*;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.customer.type.CustomerState;
import zerobase18.playticketing.customer.entity.Customer;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    // 고객 로그인 아이디
    private String loginId;

    // 고객 비밀번호
    private String password;

    // 사용자 타입
    private UserType userType;

    // 고객 상태
    private CustomerState customerState;

    // 고객 이름
    private String name;

    // 고객 생년월일
    private String birth;

    // 고객 휴대폰 번호
    private String phone;

    // 고객 이메일
    private String email;

    // 고객 주소
    private String address;

    // 고객 탈퇴 일시
    private LocalDateTime unRegisteredAt;


    public static CustomerDto fromEntity(Customer customer) {

        return CustomerDto.builder()
                .loginId(customer.getLoginId())
                .password(customer.getPassword())
                .userType(customer.getUserType())
                .customerState(customer.getCustomerState())
                .name(customer.getName())
                .birth(customer.getBirth())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .unRegisteredAt(customer.getUnRegisteredAt())
                .build();
    }

}
