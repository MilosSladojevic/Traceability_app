package com.example.Traceability.app.service;

import com.example.Traceability.app.db.dto.LoginDto;
import com.example.Traceability.app.db.entity.Employee;
import com.example.Traceability.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String getLoggedEmployeeUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        return authentication.getName();
    }

    public Employee findByUserName(String username){
        Optional<Employee> optEmployee = employeeRepository.findByUsername(username);

        if (optEmployee.isPresent()){
           Employee employee = optEmployee.get();
           return employee;
        }

        return null;
    }



}
