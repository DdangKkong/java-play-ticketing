package zerobase18.playticketing.customer.entity;


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
import zerobase18.playticketing.payment.type.ReserStat;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customers")
public class Customer extends BaseEntity implements UserDetails {

    // 고객 고유 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    // 고객 로그인 아이디
    @NotNull
    @Column(unique = true)
    private String loginId;

    // 고객 비밀 번호
    @NotNull
    private String password;

    // 사용자 타입
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;

    // 사용자 상태
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserState userState;


    // 고객 이름
    @NotNull
    private String name;

    // 고객 생년월일
    @NotNull
    private String birth;

    // 고객 휴대폰 번호
    @NotNull
    private String phone;

    // 고객 이메일
    @NotNull
    private String email;

    // 고객 주소
    @NotNull
    private String address;

    // 탈퇴 일시
    @LastModifiedDate
    private String unRegisteredAt;

    private int loginAttempt;



    // 사용자가 가지고 있는 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    // 사용자를 식별할 수 있는 사용자 이름 반환.
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

    public void incrementLoginAttempts() {
        loginAttempt++;
    }

    public void resetLoginAttempts() {
        loginAttempt = 0;
    }

}
