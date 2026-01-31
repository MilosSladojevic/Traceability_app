package com.example.Traceability.app.db.entity;

import com.example.Traceability.app.db.entity.enums.RusProblems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rus_peaces")
public class RusPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private RusProblems problem;

    private String comment;

    private LocalDateTime productionTime;

    @ManyToOne
    private Session session;

}
