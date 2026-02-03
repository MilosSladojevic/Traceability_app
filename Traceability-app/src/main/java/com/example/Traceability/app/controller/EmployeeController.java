package com.example.Traceability.app.controller;

import com.example.Traceability.app.db.dto.EmployeeDto;
import com.example.Traceability.app.db.entity.Employee;

import com.example.Traceability.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public EmployeeDto save(@RequestBody EmployeeDto employeeDto){

        System.out.println(employeeDto.getFirstName());
//        return employeeService.save(employee);

        return employeeDto;
    }
}
