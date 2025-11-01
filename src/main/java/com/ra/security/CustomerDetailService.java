package com.ra.security;

import com.ra.model.entity.Customer;
import com.ra.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomerDetailService implements UserDetailsService {
    @Autowired
    private CustomerService customerService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.findCustomerByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }else{
            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + customer.getRole().toString()));
            return CustomerPrincipal
                    .builder()
                    .customer(customer)
                    .authorities(authorities)
                    .build();
        }
    }
}
