package zerobase18.playticketing.customer.service;

import zerobase18.playticketing.auth.dto.CustomerSignUpDto;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.customer.dto.SearchCustomer;
import zerobase18.playticketing.customer.dto.UpdateCustomerDto;
import zerobase18.playticketing.customer.entity.Customer;

import java.util.List;

public interface CustomerService {

    /**
     * 고객 회원 가입
     */
    CustomerDto signUp(CustomerSignUpDto user);

    /**
     * 고객 정보 수정
     */
    CustomerDto updateCustomer(String loginId, String password, UpdateCustomerDto.Request request);


    /**
     * 고객 로그 아웃
     */
    void logout(Integer customerId);

    /**
     * 고객 정보 조회
     */
    List<CustomerDto> searchCustomer(SearchCustomer.Request request);


    /**
     * 고객 회원 탈퇴
     */
    CustomerDto deleteCustomer(String loginId, String password);




}
