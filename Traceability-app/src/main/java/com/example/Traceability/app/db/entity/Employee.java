package com.example.Traceability.app.db.entity;

import com.example.Traceability.app.db.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2 ,max = 25)
    private String firstName;

    @Column(nullable = false)
    @Size(min = 2 ,max = 25)
    private String lastName;

    @Email
    private String email;

    private String password;

    @Column(unique = true)
    private String username;

    private LocalDate dateOfAdmission;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "employee")
    private List<Session> sessions;

}
