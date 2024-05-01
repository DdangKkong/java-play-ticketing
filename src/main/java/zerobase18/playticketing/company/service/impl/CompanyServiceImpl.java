package zerobase18.playticketing.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase18.playticketing.auth.dto.CompanySignUpDto;
import zerobase18.playticketing.auth.type.UserState;
import zerobase18.playticketing.auth.type.UserType;
import zerobase18.playticketing.company.dto.CompanyDto;
import zerobase18.playticketing.company.dto.SearchCompany;
import zerobase18.playticketing.company.dto.UpdateCompanyDto;
import zerobase18.playticketing.company.entity.Company;
import zerobase18.playticketing.company.repository.CompanyRepository;
import zerobase18.playticketing.company.service.CompanyService;
import zerobase18.playticketing.global.exception.CustomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static zerobase18.playticketing.auth.type.UserState.UN_REGISTERED;
import static zerobase18.playticketing.global.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 극장 업체 회원 가입
     */
    @Override
    @Transactional
    public CompanyDto signUp(CompanySignUpDto signUpDto) {

        boolean exists = companyRepository.existsByLoginId(signUpDto.getLoginId());

        if (exists) {
            throw new CustomException(ALREADY_USE_LOGIN_ID);
        }

        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Company company = companyRepository.save(Company.builder()
                .loginId(signUpDto.getLoginId())
                .password(signUpDto.getPassword())
                .userType(UserType.COMPANY)
                .userState(UserState.REGISTERED)
                .name(signUpDto.getName())
                .company(signUpDto.getCompany())
                .phone(signUpDto.getPhone())
                .email(signUpDto.getEmail())
                .address(signUpDto.getAddress())
                .build());

        return CompanyDto.fromEntity(company);
    }

    /**
     * 극장 업체 정보 조회
     */
    @Override
    public List<CompanyDto> searchCompany(SearchCompany.Request request) {
        Company company = companyRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        validationPassword(request.getPassword(), company.getPassword());

        validateCompany(company);

        List<Company> byLoginIdAndPassword = companyRepository.findByLoginIdAndPassword(company.getLoginId(), company.getPassword());



        return byLoginIdAndPassword.stream()
                .map(CompanyDto::fromEntity)
                .collect(Collectors.toList());
    }


    /**
     * 극장 업체 정보 수정
     */
    @Override
    @Transactional
    public CompanyDto updateCompany(String loginId, String password, UpdateCompanyDto.Request request) {
        Company company = companyRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        validateCompany(company);

        validationPassword(password, company.getPassword());

        company.setPassword(passwordEncoder.encode(request.getPassword()));
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setAddress(request.getAddress());

        Company save = companyRepository.save(company);

        return CompanyDto.fromEntity(save);
    }

    /**
     * 극장 업체 회원 탈퇴
     */
    @Override
    @Transactional
    public CompanyDto deleteCompany(String loginId, String password) {
        Company company = companyRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));


        validationPassword(password, company.getPassword());

        validateCompany(company);

        company.setUserState(UN_REGISTERED);
        company.setUnRegisteredAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        companyRepository.save(company);


        return CompanyDto.fromEntity(company);
    }

    private void validateCompany(Company company) {
        if (company.getUserState().equals(UN_REGISTERED)) {
            throw new CustomException(UN_REGISTERED_USER);
        }
    }

    private void validationPassword(String password, String checkPassword) {
        if (!passwordEncoder.matches(password, checkPassword)) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }
    }
}
