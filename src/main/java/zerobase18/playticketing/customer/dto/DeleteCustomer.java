package zerobase18.playticketing.customer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.global.entity.BaseEntity;

import java.time.LocalDateTime;

public class DeleteCustomer {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {


        @NotNull
        private String loginId;

        @NotNull
        private String password;

    }


    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String loginId;

        private String name;

        private String unRegisteredAt;


        public static Response from(CustomerDto customerDto) {

            return Response.builder()
                    .loginId(customerDto.getLoginId())
                    .name(customerDto.getName())
                    .unRegisteredAt(customerDto.getUnRegisteredAt())
                    .build();
        }

    }

}
