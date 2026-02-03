package com.example.Traceability.app.db.entity;

import com.example.Traceability.app.db.entity.enums.ReasonForToolReplacement;
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
@Table(name="setup_pieces")
public class SetupPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private ReasonForToolReplacement reason;

    private String comment;

    private LocalDateTime productionTime;

    @ManyToOne
    private Session session;
}
