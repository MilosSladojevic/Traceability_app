package com.example.Traceability.app.sevice;

import com.example.Traceability.app.db.dto.DeleteEmployeeDto;
import com.example.Traceability.app.db.dto.EmployeeDataForTable;
import com.example.Traceability.app.db.dto.EmployeeDto;
import com.example.Traceability.app.db.entity.Employee;
import com.example.Traceability.app.repository.EmployeeRepository;
import com.example.Traceability.app.service.EmployeeService;
import org.springframework.security.core.Authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    private AutoCloseable mocks;

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
        SecurityContextHolder.clearContext();
    }

    @Test
    void test_findByUserName(){
        Employee testEmploy = new Employee();
        testEmploy.setUsername("milos");
        testEmploy.setId(1L);
        testEmploy.setFirstName("Milos");

        Mockito.when(employeeRepository.findByUsername(testEmploy.getUsername())).thenReturn(Optional.of(testEmploy));

        Employee employee = employeeService.findByUserName(testEmploy.getUsername());

        assertEquals(employee.getUsername(), testEmploy.getUsername());
        assertEquals(employee.getId(),testEmploy.getId());
        assertEquals(employee.getFirstName(),testEmploy.getFirstName());
    }

    @Test
    void testDeleteEmployeeSuccess(){
        DeleteEmployeeDto dto = new DeleteEmployeeDto("Milos","Sladojevic",1L);
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Milos");
        employee.setLastName("Sladojevic");

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.checkAndDeleteEmployee(dto);

        Mockito.verify(employeeRepository).deleteById(1L);

    }

    @Test
    void testDeleteEmployeeNameMismatch(){
        DeleteEmployeeDto dto = new DeleteEmployeeDto("Pera","Peric",1L);
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Milos");
        employee.setLastName("Sladojevic");

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.checkAndDeleteEmployee(dto);

        Mockito.verify(employeeRepository,Mockito.never()).deleteById(1L);

    }

    @Test
    void testDeleteEmployeeNotFound() {
        DeleteEmployeeDto dto = new DeleteEmployeeDto( "Marko", "Markovic",1L);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        employeeService.checkAndDeleteEmployee(dto);

        Mockito.verify(employeeRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    void test_createNew(){
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Milos");
        dto.setLastName("Sladojevic");

        Employee mappedEmployee = new Employee();
        mappedEmployee.setFirstName("Milos");
        mappedEmployee.setLastName("Sladojevic");

        when(modelMapper.map(dto, Employee.class)).thenReturn(mappedEmployee);

        employeeService.createNew(dto);

        verify(employeeRepository,times(1)).save(mappedEmployee);
    }

    @Test
    void test_getAllEmployees(){
        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Milos");
        employee1.setLastName("Sladojevic");
        employee1.setDateOfAdmission(LocalDate.of(2020,1,15));

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Marko");
        employee2.setLastName("Sladojevic");
        employee2.setDateOfAdmission(LocalDate.of(2022,1,15));

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1,employee2));

        List<EmployeeDataForTable> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());

        EmployeeDataForTable dto1 = result.get(0);
        assertEquals(1L,dto1.getId());
        assertEquals("Milos",dto1.getFirstName());
        assertEquals("Sladojevic",dto1.getLastName());
        assertEquals(LocalDate.of(2020,1,15),dto1.getDateOfAdmission());


        EmployeeDataForTable dto2 = result.get(1);
        assertEquals(2L,dto2.getId());
        assertEquals("Marko",dto2.getFirstName());
        assertEquals("Sladojevic",dto2.getLastName());
        assertEquals(LocalDate.of(2022,1,15),dto2.getDateOfAdmission());

        verify(employeeRepository,times(1)).findAll();


    }


    @Test
    void testGetLoggedEmployeeUsername_authenticated() {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("milos");


        String username = employeeService.getLoggedEmployeeUsername();


        assertEquals("milos", username);
    }


    @Test
    void testGetLoggedEmployeeUsername_noAuthentication() {

        when(securityContext.getAuthentication()).thenReturn(null);


        String username = employeeService.getLoggedEmployeeUsername();


        assertNull(username);
    }

    @Test
    void testGetLoggedEmployeeUsername_notAuthenticated() {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);


        String username = employeeService.getLoggedEmployeeUsername();


        assertNull(username);
    }






}
