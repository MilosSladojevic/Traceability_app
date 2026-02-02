package com.example.Traceability.app.service;

import com.example.Traceability.app.db.entity.Employee;
import com.example.Traceability.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserNasDetailsService implements UserDetailsService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public UserNasDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Employee with username "+ username +" not found!"));

        return org.springframework.security.core.userdetails.User.builder()
                .password(employee.getPassword())
                .username(employee.getUsername())
                .roles(String.valueOf(employee.getRole()))
                .build();
    }


}
