package zerobase18.playticketing.admin.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.customer.dto.CustomerDto;


public class UpdateAdminDto {

    @Getter
    @AllArgsConstructor
    @Setter
    public static class Request {

        private String password;

        private String email;

        private String phone;

        private String address;

    }
    @Getter
    @AllArgsConstructor
    @Builder
    @Setter
    public static class Response {

        private String password;

        private String email;

        private String phone;

        private String address;

        public static Response fromEntity(AdminDto adminDto) {
            return Response.builder()
                    .password(adminDto.getPassword())
                    .email(adminDto.getEmail())
                    .phone(adminDto.getPhone())
                    .address(adminDto.getAddress())
                    .build();
        }
    }




}
