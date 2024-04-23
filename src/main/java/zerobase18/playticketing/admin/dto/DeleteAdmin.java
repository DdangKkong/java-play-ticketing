package zerobase18.playticketing.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.customer.dto.CustomerDto;

public class DeleteAdmin {

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


        public static Response from(AdminDto adminDto) {

            return Response.builder()
                    .loginId(adminDto.getLoginId())
                    .name(adminDto.getName())
                    .unRegisteredAt(adminDto.getUnRegisteredAt())
                    .build();
        }

    }

}
