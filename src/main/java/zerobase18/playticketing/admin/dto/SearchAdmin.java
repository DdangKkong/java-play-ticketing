package zerobase18.playticketing.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class SearchAdmin {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {

        @NotNull
        private String loginId;

        @NotNull
        private String password;
    }
}
