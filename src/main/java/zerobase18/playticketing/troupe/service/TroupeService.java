package zerobase18.playticketing.troupe.service;

import zerobase18.playticketing.auth.dto.TroupeSignUpDto;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.troupe.dto.SearchTroupe;
import zerobase18.playticketing.troupe.dto.TroupeDto;
import zerobase18.playticketing.troupe.dto.UpdateTroupeDto;

import java.util.List;

public interface TroupeService {

    /**
     * 연극 업체 회원 가입
     */
    TroupeDto signUp(TroupeSignUpDto signUpDto);

    /**
     * 연극 업체 정보 조회
     */
    List<TroupeDto> searchTroupe(SearchTroupe.Request request);

    /**
     * 연극 업체 정보 수정
     */
    TroupeDto updateTroupe(String loginId, String password, UpdateTroupeDto.Request request);

    /**
     * 연극 업체 회원 탈퇴
     */
    TroupeDto deleteTroupe(String loginId, String password);



}
