package zerobase18.playticketing.auth.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {

    CUSTOMER("ROLE_CUSTOMER"),
    COMPANY("ROLE_COMPANY"),
    TROUPE("ROLE_TROUPE"),
    ADMIN("ROLE_ADMIN");



    private final String description;
}
