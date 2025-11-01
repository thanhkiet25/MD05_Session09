package com.ra.service.imp;

import com.ra.model.constant.Role;
import com.ra.model.dto.request.CustomerDTO;
import com.ra.model.dto.request.CustomerLoginDTO;
import com.ra.model.dto.response.CustomerLoginResponse;
import com.ra.model.entity.Customer;
import com.ra.repository.CustomerRepository;
import com.ra.security.jwt.JWTProvider;
import com.ra.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer register(CustomerDTO customerDTO) {
        Customer customer = Customer
                .builder()
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .password(passwordEncoder.encode(customerDTO.getPassword()))
                .phoneNumber(customerDTO.getPhoneNumber())
                .role(Role.USER)
                .build();
        try{
          return  customerRepository.save(customer);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerLoginResponse login(CustomerLoginDTO customerLoginDTO) {
        Customer customer = findCustomerByEmail(customerLoginDTO.getEmail());
        if(customer!= null && passwordEncoder.matches(customerLoginDTO.getPassword(), customer.getPassword())){
            return CustomerLoginResponse
                    .builder()
                    .email(customer.getEmail())
                    .accessToken(jwtProvider.generateToken(customer.getEmail()))
                    .build();
        }else{
            return null;
        }
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}
