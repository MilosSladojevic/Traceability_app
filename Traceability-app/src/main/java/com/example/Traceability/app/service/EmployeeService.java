package com.example.Traceability.app.service;

import com.example.Traceability.app.db.dto.DeleteEmployeeDto;
import com.example.Traceability.app.db.dto.EmployeeDataForTable;
import com.example.Traceability.app.db.dto.EmployeeDto;
import com.example.Traceability.app.db.dto.LoginDto;
import com.example.Traceability.app.db.entity.Employee;
import com.example.Traceability.app.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
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


    @Transactional
    public void createNew(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);

        employeeRepository.save(employee);
        // cuvam ga 2 puta da bi ubacio id u username
        employee.setUsername(employee.getUsername()+"-"+employee.getId()+")");
        employeeRepository.save(employee);
    }

    public void checkAndDeleteEmployee(DeleteEmployeeDto deleteEmployeeDto) {
        Optional <Employee> employeeOptional =employeeRepository.findById(deleteEmployeeDto.getEmployeeId());
        if (employeeOptional.isPresent()){
            Employee employee = employeeOptional.get();
            if (employee.getFirstName().equalsIgnoreCase(deleteEmployeeDto.getFirstName()) && employee.getLastName().equalsIgnoreCase(deleteEmployeeDto.getLastName())){
                employeeRepository.deleteById(deleteEmployeeDto.getEmployeeId());
            }
        }

    }

    public List<EmployeeDataForTable> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();


        List<EmployeeDataForTable> employeeDataForTables = employees.stream().map( employee -> {
            EmployeeDataForTable dto = new EmployeeDataForTable();
            dto.setId(employee.getId());
            dto.setFirstName(employee.getFirstName());
            dto.setLastName(employee.getLastName());
            dto.setDateOfAdmission(employee.getDateOfAdmission());
            return dto;
        }).toList();

        return employeeDataForTables;
    }
}
