package com.example.Traceability.app.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;

import java.lang.reflect.Type;
import java.util.List;

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

    private String type;

    @Column(nullable = false)
    private Double nominal;

    @Column(nullable = false)
    private Double minTolerance;

    @Column(nullable = false)
    private Double maxTolerance;

    @Column(nullable = false)
    private Double realValue;

    @ManyToOne
    private ControlledPiece controlledPiece;

    @ManyToOne
    private SetupPiece setupPiece;

    @ManyToMany
    @JoinTable(name = "measure_reference",
            joinColumns = @JoinColumn(name = "measure_id"),
            inverseJoinColumns = @JoinColumn(name = "reference_id"))
    private List<Reference> references;

}
