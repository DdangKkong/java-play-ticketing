package zerobase18.playticketing.company.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerobase18.playticketing.auth.dto.SignInDto;
import zerobase18.playticketing.auth.dto.CompanySignUpDto;
import zerobase18.playticketing.auth.security.TokenProvider;
import zerobase18.playticketing.auth.service.AuthService;
import zerobase18.playticketing.company.dto.CompanyInfo;
import zerobase18.playticketing.company.dto.DeleteCompany;
import zerobase18.playticketing.company.dto.SearchCompany;
import zerobase18.playticketing.company.dto.UpdateCompanyDto;
import zerobase18.playticketing.company.entity.Company;
import zerobase18.playticketing.company.service.CompanyService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    private final TokenProvider tokenProvider;

    private final AuthService authService;

    /**
     * 극장 업체 회원 가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> companySignUp(@RequestBody @Valid CompanySignUpDto signUpDto) {

        return ResponseEntity.ok().body(
                signUpDto.from(companyService.signUp(signUpDto))
        );
    }


    /**
     * 극장 업체 로그인
     */
    @PostMapping("/signin")
    public ResponseEntity<?> companySignIn(@RequestBody @Valid SignInDto signInDto) {

        Company company = authService.authenticatedCompany(signInDto);

        return ResponseEntity.ok(
                tokenProvider.createToken(
                        company.getLoginId(),
                        company.getUserType()
                )
        );
    }

    /**
     * 극장 업체 정보 조회
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_COMPANY')")
    public List<CompanyInfo> SearchCompany(
            @RequestBody @Valid SearchCompany.Request request
    ) {

        return companyService.searchCompany(request)
                .stream().map(
                        companyDto -> CompanyInfo.builder()
                                .name(companyDto.getName())
                                .company(companyDto.getCompany())
                                .phone(companyDto.getPhone())
                                .email(companyDto.getEmail())
                                .address(companyDto.getAddress()).build()
                ).collect(Collectors.toList());
    }

    /**
     * 정보 수정
     */
    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_COMPANY')")
    public UpdateCompanyDto.Response updateTroupe(
            @RequestParam @Valid String loginId,
            @RequestParam @Valid String password,
            @RequestBody @Valid UpdateCompanyDto.Request request
    ) {
        return UpdateCompanyDto.Response.fromEntity(
                companyService.updateCompany(loginId, password, request)
        );
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_COMPANY')")
    public DeleteCompany.Response deleteTroupe(
            @RequestBody @Valid DeleteCompany.Request request
    ) {
        return DeleteCompany.Response.from(
                companyService.deleteCompany(request.getLoginId(), request.getPassword())
        );
    }

}
