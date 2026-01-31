package com.example.Traceability.app.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "controlled_pieces")
public class ControlledPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String qrCode;

    @OneToMany(mappedBy = "controlledPiece", cascade = CascadeType.ALL)
    private List<Measure> measures;

    private LocalDateTime productionTime;

    @ManyToOne
    private Session session;

}
