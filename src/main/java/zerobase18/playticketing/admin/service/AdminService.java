package zerobase18.playticketing.admin.service;

import zerobase18.playticketing.admin.dto.AdminDto;
import zerobase18.playticketing.admin.dto.SearchAdmin;
import zerobase18.playticketing.admin.dto.UpdateAdminDto;
import zerobase18.playticketing.auth.dto.AdminSignUpDto;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.customer.dto.UpdateCustomerDto;

import java.util.List;

public interface AdminService {

    /**
     * 관리자 회원 가입
     */
    AdminDto signUp(AdminSignUpDto user);

    /**
     * 관리자 로그 아웃
     */
    void logout(Integer adminId);


    /**
     * 관리자 정보 조회
     */
    List<AdminDto> searchAdmin(SearchAdmin.Request request);

    /**
     * 관리자 정보 수정
     */
    AdminDto updateAdmin(String loginId, String password, UpdateAdminDto.Request request);


    /**
     * 관리자 회원 탈퇴
     */
    AdminDto deleteAdmin(String loginId, String password);
}
