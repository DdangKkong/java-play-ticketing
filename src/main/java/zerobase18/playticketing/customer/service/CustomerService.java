package zerobase18.playticketing.customer.service;

import zerobase18.playticketing.auth.dto.CustomerSignUpDto;
import zerobase18.playticketing.customer.dto.CustomerDto;

public interface CustomerService {

    CustomerDto signUp(CustomerSignUpDto user);
}
