package zerobase18.playticketing.auth.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignInDto {

    @Column(unique = true)
    @NotNull
    private String loginId;

    @NotNull
    private String password;
}
