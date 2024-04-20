package zerobase18.playticketing.troupe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TroupeInfo {

    private String name;

    private String company;

    private String phone;

    private String email;

    private String address;

}
