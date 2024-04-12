package zerobase18.playticketing.seller.service;

import zerobase18.playticketing.auth.dto.CustomerSignUpDto;
import zerobase18.playticketing.auth.dto.SellerSignUpDto;
import zerobase18.playticketing.customer.dto.CustomerDto;
import zerobase18.playticketing.seller.dto.SellerDto;

public interface SellerService {

    SellerDto signUp(SellerSignUpDto signUpDto);


}
