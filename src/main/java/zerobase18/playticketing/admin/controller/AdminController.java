package zerobase18.playticketing.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.admin.dto.AdminInfo;
import zerobase18.playticketing.admin.dto.DeleteAdmin;
import zerobase18.playticketing.admin.dto.SearchAdmin;
import zerobase18.playticketing.admin.dto.UpdateAdminDto;
import zerobase18.playticketing.admin.entity.Admin;
import zerobase18.playticketing.admin.service.AdminService;
import zerobase18.playticketing.auth.dto.AdminSignUpDto;
import zerobase18.playticketing.auth.dto.SignInDto;
import zerobase18.playticketing.auth.security.TokenProvider;
import zerobase18.playticketing.auth.service.AuthService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    private final TokenProvider tokenProvider;

    private final AuthService authService;

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 관리자 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> adminSignUp(@RequestBody @Valid AdminSignUpDto adminSignUpDto) {

        return ResponseEntity.ok().body(
                adminSignUpDto.fromEntity(adminService.signUp(adminSignUpDto))
        );
    }

    /**
     * 로그인
     */
    @PostMapping("/signin")
    public ResponseEntity<?> AdminSignIn(@RequestBody @Valid SignInDto sign) {

        Admin admin = authService.authenticatedAdmin(sign);

        String token = tokenProvider.createToken(admin.getLoginId(), admin.getUserType());

        redisTemplate.opsForValue().set("JWT_TOKEN:" + admin.getLoginId(), token, tokenProvider.getTokenValidTime());


        return ResponseEntity.ok(
                token
        );
    }

    /**
     * 로그 아웃
     */
    @PostMapping("/logout")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> logout(@RequestParam @Valid Integer adminId) {

        adminService.logout(adminId);
        return ResponseEntity.ok().build();
    }

    /**
     * 관리자 정보 조회
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AdminInfo> SearchCustomer(
            @RequestBody @Valid SearchAdmin.Request request
    ) {

        return adminService.searchAdmin(request)
                .stream().map(
                        adminDto -> AdminInfo.builder()
                                .name(adminDto.getName())
                                .phone(adminDto.getPhone())
                                .email(adminDto.getEmail())
                                .address(adminDto.getAddress()).build()
                ).collect(Collectors.toList());
    }


    /**
     * 관리자 정보 수정
     */
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UpdateAdminDto.Response updateAdmin(
            @RequestParam @Valid String loginId,
            @RequestParam @Valid String password,
            @RequestBody @Valid UpdateAdminDto.Request request
    ) {
        return UpdateAdminDto.Response.fromEntity(
                adminService.updateAdmin(loginId, password, request)
        );
    }


    /**
     * 관리자 회원 탈퇴
     */

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DeleteAdmin.Response deleteAdmin(
            @RequestBody @Valid DeleteAdmin.Request request
    ) {
        return DeleteAdmin.Response.from(
                adminService.deleteAdmin(request.getLoginId(), request.getPassword())
        );
    }


}
