package zerobase18.playticketing.customer.dto;

import lombok.*;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.customer.entity.Customer;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private String loginId;

    private String password;

    private UserType userType;

    private String name;

    private String birth;

    private String phone;

    private String email;

    private String address;


    public static CustomerDto fromEntity(Customer customer) {

        return CustomerDto.builder()
                .loginId(customer.getLoginId())
                .password(customer.getPassword())
                .userType(customer.getUserType())
                .name(customer.getName())
                .birth(customer.getBirth())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .build();
    }

}
