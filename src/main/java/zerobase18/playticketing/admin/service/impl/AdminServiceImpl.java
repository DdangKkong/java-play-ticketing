package zerobase18.playticketing.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.admin.dto.AdminDto;
import zerobase18.playticketing.admin.dto.SearchAdmin;
import zerobase18.playticketing.admin.dto.UpdateAdminDto;
import zerobase18.playticketing.admin.entity.Admin;
import zerobase18.playticketing.admin.repository.AdminRepository;
import zerobase18.playticketing.admin.service.AdminService;
import zerobase18.playticketing.auth.dto.AdminSignUpDto;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.global.exception.CustomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static zerobase18.playticketing.auth.type.UserState.ACTIVE;
import static zerobase18.playticketing.auth.type.UserType.ADMIN;
import static zerobase18.playticketing.global.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;




    /**
     * 관리자 회원 가입
     */
    @Override
    @Transactional
    public AdminDto signUp(AdminSignUpDto user) {
        boolean exists = adminRepository.existsByLoginId(user.getLoginId());

        if (exists) {
            throw new CustomException(ALREADY_USE_LOGIN_ID);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Admin admin = adminRepository.save(Admin.builder()
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .userType(ADMIN)
                .userState(ACTIVE)
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .build());
        return AdminDto.fromEntity(admin);
    }

    /**
     * 관리자 정보 조회
     */
    @Override
    @Transactional
    public List<AdminDto> searchAdmin(SearchAdmin.Request request) {
        Admin admin = adminRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        validateAdmin(admin);

        List<Admin> byLoginIdAndPassword = adminRepository.findByLoginIdAndPassword(admin.getLoginId(), admin.getPassword());



        return byLoginIdAndPassword.stream()
                .map(AdminDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 관리자 정보 수정
     */
    @Override
    @Transactional
    public AdminDto updateAdmin(String loginId, String password, UpdateAdminDto.Request request) {

        Admin admin = adminRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        validateAdmin(admin);

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setEmail(request.getEmail());
        admin.setPhone(request.getPhone());
        admin.setAddress(request.getEmail());

        Admin save = adminRepository.save(admin);

        return AdminDto.fromEntity(save);
    }

    /**
     * 관리자 회원 탈퇴
     */
    @Override
    @Transactional
    public AdminDto deleteAdmin(String loginId, String password) {

        Admin admin = adminRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        validateAdmin(admin);

        admin.setUserState(UserState.UN_REGISTERED);
        admin.setUnRegisteredAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        adminRepository.save(admin);


        return AdminDto.fromEntity(admin);
    }


    private void validateAdmin(Admin admin) {
        if (admin.getUserState().equals(UserState.UN_REGISTERED)) {
            throw new CustomException(UN_REGISTERED_USER);
        }
    }
}
