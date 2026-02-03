package com.example.Traceability.app.db.entity;

import com.example.Traceability.app.db.entity.enums.RfProblems;
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
@Table(name = "rf_pieces")
public class RfPiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private RfProblems problems;

    private LocalDateTime productionTime;

    private String comment;

    @ManyToOne
    private Session session;


}
