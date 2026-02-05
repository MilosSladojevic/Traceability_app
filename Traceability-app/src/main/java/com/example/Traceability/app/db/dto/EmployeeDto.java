package com.example.Traceability.app.db.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String role;
    private LocalDate admissionDate;
    private String confirmPassword;
}
