package com.ra.service;

import com.ra.model.dto.request.CustomerDTO;
import com.ra.model.dto.request.CustomerLoginDTO;
import com.ra.model.dto.response.CustomerLoginResponse;
import com.ra.model.entity.Customer;

public interface CustomerService {
    Customer register(CustomerDTO customerDTO);

    CustomerLoginResponse login(CustomerLoginDTO customerLoginDTO);

    Customer findCustomerByEmail(String email);

}
