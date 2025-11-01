package com.ra.controller;

import com.ra.model.dto.request.CustomerDTO;
import com.ra.model.dto.request.CustomerLoginDTO;
import com.ra.model.dto.response.CustomerLoginResponse;
import com.ra.model.entity.Customer;
import com.ra.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;
@PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerDTO customerDTO){
    Customer customer = customerService.register(customerDTO);
    if(customer != null){
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }else{
        return new ResponseEntity<>("Register failed",HttpStatus.BAD_REQUEST);
    }
}

@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginDTO customerLoginDTO){
    CustomerLoginResponse customerLoginResponse = customerService.login(customerLoginDTO);
    if(customerLoginResponse != null){
        return new ResponseEntity<>(customerLoginResponse, HttpStatus.OK);
    }else{
        return new ResponseEntity<>("Password or Email not match!",HttpStatus.BAD_REQUEST);
    }
}
}
