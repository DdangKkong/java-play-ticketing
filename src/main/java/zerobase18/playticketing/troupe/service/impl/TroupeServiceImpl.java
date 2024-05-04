package zerobase18.playticketing.troupe.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.auth.dto.TroupeSignUpDto;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.troupe.dto.SearchTroupe;
import zerobase18.playticketing.troupe.dto.TroupeDto;
import zerobase18.playticketing.troupe.dto.UpdateTroupeDto;
import zerobase18.playticketing.troupe.entity.Troupe;
import zerobase18.playticketing.troupe.repository.TroupeRepository;
import zerobase18.playticketing.troupe.service.TroupeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static zerobase18.playticketing.auth.type.UserState.ACTIVE;
import static zerobase18.playticketing.auth.type.UserState.UN_REGISTERED;
import static zerobase18.playticketing.global.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TroupeServiceImpl implements TroupeService {

    private final TroupeRepository troupeRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 연극 업체 회원 가입
     */
    @Override
    @Transactional
    public TroupeDto signUp(TroupeSignUpDto signUpDto) {

        boolean exists = troupeRepository.existsByLoginId(signUpDto.getLoginId());

        if (exists) {
            throw new CustomException(ALREADY_USE_LOGIN_ID);
        }

        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Troupe troupe = troupeRepository.save(Troupe.builder()
                .loginId(signUpDto.getLoginId())
                .password(signUpDto.getPassword())
                .userType(UserType.TROUPE)
                .userState(ACTIVE)
                .name(signUpDto.getName())
                .company(signUpDto.getCompany())
                .phone(signUpDto.getPhone())
                .email(signUpDto.getEmail())
                .address(signUpDto.getAddress())
                .build());

        return TroupeDto.fromEntity(troupe);
    }

    /**
     * 연극 업체 정보 조회
     */
    @Override
    @Transactional
    public List<TroupeDto> searchTroupe(SearchTroupe.Request request) {


        Troupe troupe = troupeRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), troupe.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        validateTroupe(troupe);

        List<Troupe> byLoginIdAndPassword = troupeRepository.findByLoginIdAndPassword(troupe.getLoginId(), troupe.getPassword());



        return byLoginIdAndPassword.stream()
                .map(TroupeDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 연극 업체 정보 수정
     */
    @Override
    @Transactional
    public TroupeDto updateTroupe(String loginId, String password, UpdateTroupeDto.Request request) {

        Troupe troupe = troupeRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        validateTroupe(troupe);

        if (!passwordEncoder.matches(password, troupe.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        troupe.setPassword(passwordEncoder.encode(request.getPassword()));
        troupe.setEmail(request.getEmail());
        troupe.setPhone(request.getPhone());
        troupe.setAddress(request.getAddress());

        Troupe save = troupeRepository.save(troupe);

        return TroupeDto.fromEntity(save);
    }

    /**
     * 연극 업체 회원 탈퇴
     */
    @Override
    @Transactional
    public TroupeDto deleteTroupe(String loginId, String password) {
        Troupe troupe = troupeRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        if (!passwordEncoder.matches(password, troupe.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        validateTroupe(troupe);

        troupe.setUserState(UN_REGISTERED);
        troupe.setUnRegisteredAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        troupeRepository.save(troupe);


        return TroupeDto.fromEntity(troupe);
    }

    // 유효성 검사
    private void validateTroupe(Troupe troupe) {
        if (troupe.getUserState().equals(UN_REGISTERED)) {
            throw new CustomException(UN_REGISTERED_USER);
        }
    }

}
