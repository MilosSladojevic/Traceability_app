package com.example.Traceability.app.db.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteEmployeeDto {


    private String firstName;


    private String lastName;


    private Long employeeId;
}
