package com.example.Traceability.app.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measure")
public class Measure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dimension;

    @Column(nullable = false)
    private String realValue;

    @ManyToOne
    private ControlledPiece controlledPiece;

    @ManyToOne
    private SetupPiece setupPiece;

}
