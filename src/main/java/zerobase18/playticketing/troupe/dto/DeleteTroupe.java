package zerobase18.playticketing.troupe.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.customer.dto.CustomerDto;

import java.time.LocalDateTime;

public class DeleteTroupe {

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


        public static Response from(TroupeDto troupeDto) {

            return Response.builder()
                    .loginId(troupeDto.getLoginId())
                    .name(troupeDto.getName())
                    .unRegisteredAt(troupeDto.getUnRegisteredAt())
                    .build();
        }

    }

}
