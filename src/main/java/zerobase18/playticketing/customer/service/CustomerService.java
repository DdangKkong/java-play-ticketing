package zerobase18.playticketing.customer.service;

import zerobase18.playticketing.auth.dto.SignUpDto;
import zerobase18.playticketing.customer.dto.CustomerDto;

public interface CustomerService {

    CustomerDto signUp(SignUpDto user);
}
