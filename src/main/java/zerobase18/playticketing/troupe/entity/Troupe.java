package zerobase18.playticketing.troupe.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.global.entity.BaseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Troupe extends BaseEntity implements UserDetails {

    // 연극 업체 고유 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer troupeId;

    // 연극 업체 로그인 아이디
    @NotNull
    @Column(unique = true)
    private String loginId;

    // 연극 업체 비밀 번호
    @NotNull
    private String password;

    // 사용자 타입
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;

    // 연극 업체 상태
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserState userState;

    // 이름
    @NotNull
    private String name;

    // 회사
    @NotNull
    private String company;

    // 휴대폰 번호
    @NotNull
    private String phone;

    // 이메일
    @NotNull
    private String email;

    // 주소
    @NotNull
    private String address;


    // 회원 탈퇴 일시
    @LastModifiedDate
    private String unRegisteredAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_TROUPE"));
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }




}
