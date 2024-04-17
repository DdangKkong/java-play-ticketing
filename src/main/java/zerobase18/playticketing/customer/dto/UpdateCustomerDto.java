package zerobase18.playticketing.customer.dto;


import lombok.*;


public class UpdateCustomerDto {

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

        public static Response fromEntity(CustomerDto customerDto) {
            return Response.builder()
                    .password(customerDto.getPassword())
                    .email(customerDto.getEmail())
                    .phone(customerDto.getPhone())
                    .address(customerDto.getAddress())
                    .build();
        }
    }




}
