package zerobase18.playticketing.company.service;

import zerobase18.playticketing.auth.dto.CompanySignUpDto;
import zerobase18.playticketing.company.dto.CompanyDto;
import zerobase18.playticketing.company.dto.SearchCompany;
import zerobase18.playticketing.company.dto.UpdateCompanyDto;

import java.util.List;

public interface CompanyService {

    /**
     * 극장 회원 가입
     */
    CompanyDto signUp(CompanySignUpDto signUpDto);


    /**
     * 극장 업체 정보 조회
     */
    List<CompanyDto> searchCompany(SearchCompany.Request request);


    /**
     * 극장 업체 정보 수정
     */
    CompanyDto updateCompany(String loginId, String password, UpdateCompanyDto.Request request);

    /**
     * 극장 업체 회원 탈퇴
     */
    CompanyDto deleteCompany(String loginId, String password);


}
