package zerobase18.playticketing.company.service;

import zerobase18.playticketing.auth.dto.SellerSignUpDto;
import zerobase18.playticketing.company.dto.CompanyDto;
import zerobase18.playticketing.company.dto.SearchCompany;
import zerobase18.playticketing.company.dto.UpdateCompanyDto;
import zerobase18.playticketing.troupe.dto.TroupeDto;
import zerobase18.playticketing.troupe.dto.UpdateTroupeDto;

import java.util.List;

public interface CompanyService {

    /**
     * 극장 회원 가입
     */
    CompanyDto signUp(SellerSignUpDto signUpDto);


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
