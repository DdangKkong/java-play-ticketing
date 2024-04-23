package zerobase18.playticketing.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.troupe.dto.TroupeDto;

public class DeleteCompany {

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


        public static Response from(CompanyDto companyDto) {

            return Response.builder()
                    .loginId(companyDto.getLoginId())
                    .name(companyDto.getName())
                    .unRegisteredAt(companyDto.getUnRegisteredAt())
                    .build();
        }

    }

}
