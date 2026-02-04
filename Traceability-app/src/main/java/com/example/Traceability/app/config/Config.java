package com.example.Traceability.app.config;

import com.example.Traceability.app.db.dto.EmployeeDto;
import com.example.Traceability.app.db.entity.Employee;
import com.example.Traceability.app.db.entity.enums.Role;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import org.modelmapper.convention.MatchingStrategies;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(false);

        TypeMap<Employee, EmployeeDto> employeeToDto =
                modelMapper.createTypeMap(Employee.class, EmployeeDto.class);

        employeeToDto.addMappings( mapper ->{
            mapper.map(Employee::getFirstName,EmployeeDto::setFirstName);
            mapper.map(Employee::getLastName,EmployeeDto::setLastName);
            mapper.map(Employee::getEmail,EmployeeDto::setEmail);
            mapper.map(Employee::getDateOfAdmission,EmployeeDto::setAdmissionDate);
            mapper.map(Employee::getPassword,EmployeeDto::setPassword);
            mapper.map(src -> src.getRole() == null ? null : src.getRole().toString(), EmployeeDto::setRole);
            mapper.map(Employee::getUsername,EmployeeDto::setUsername);
            mapper.skip(EmployeeDto::setConfirmPassword);
        });
        Converter<String, Role> roleConverter = ctx -> {
            String source = ctx.getSource();
            if (source == null) return null;
            try {
                return Role.valueOf(source.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        };

        TypeMap<EmployeeDto, Employee> dtoToEmployee = modelMapper.createTypeMap(EmployeeDto.class, Employee.class);



        dtoToEmployee.addMappings(mapping -> {
            mapping.map(EmployeeDto::getRole,Employee::setRole);
            mapping.using(roleConverter).map(EmployeeDto::getRole,Employee::setRole);
            mapping.skip(Employee::setId);
            mapping.map(EmployeeDto::getAdmissionDate,Employee::setDateOfAdmission);

        });


//        modelMapper.validate();


        return modelMapper;




    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
