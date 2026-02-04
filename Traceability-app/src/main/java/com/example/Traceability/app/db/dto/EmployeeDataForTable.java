package com.example.Traceability.app.db.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDataForTable {
    private String firstName;
    private String lastName;
    private Long id;
    private LocalDate dateOfAdmission;
}
