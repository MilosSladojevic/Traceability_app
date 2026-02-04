package com.example.Traceability.app.controller;

import com.example.Traceability.app.db.dto.DeleteEmployeeDto;
import com.example.Traceability.app.db.dto.EmployeeDataForTable;
import com.example.Traceability.app.db.dto.EmployeeDto;
import com.example.Traceability.app.db.entity.Employee;

import com.example.Traceability.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


         employeeService.createNew(employeeDto);

        return employeeDto;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEmployee(@RequestBody DeleteEmployeeDto deleteEmployeeDto){
        employeeService.checkAndDeleteEmployee(deleteEmployeeDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all")
    private List<EmployeeDataForTable> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
}
