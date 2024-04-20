package zerobase18.playticketing.troupe.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public class UpdateTroupeDto {

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

        public static Response fromEntity(TroupeDto troupeDto) {
            return Response.builder()
                    .password(troupeDto.getPassword())
                    .email(troupeDto.getEmail())
                    .phone(troupeDto.getPhone())
                    .address(troupeDto.getAddress())
                    .build();
        }
    }




}
