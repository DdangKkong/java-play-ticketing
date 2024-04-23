package zerobase18.playticketing.company.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase18.playticketing.troupe.dto.TroupeDto;


public class UpdateCompanyDto {

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

        public static Response fromEntity(CompanyDto companyDto) {
            return Response.builder()
                    .password(companyDto.getPassword())
                    .email(companyDto.getEmail())
                    .phone(companyDto.getPhone())
                    .address(companyDto.getAddress())
                    .build();
        }
    }




}
