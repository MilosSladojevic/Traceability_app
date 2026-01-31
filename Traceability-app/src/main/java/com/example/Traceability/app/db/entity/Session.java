package com.example.Traceability.app.db.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_inbox",nullable = false)
    private String noInbox;

    @Column(name = "number_of_outbox", nullable = false)
    private String noOutbox;



    @ManyToOne
    private Reference reference;

    @ManyToOne
    private Employee employee;

    @OneToMany(mappedBy = "session")
    private Set<Piece> okPiece;

    @OneToMany(mappedBy = "session")
    private Set<ControlledPiece> controlledPieces;

    @OneToMany(mappedBy = "session")
    private Set<SetupPiece> setupPieces;

    @OneToMany(mappedBy = "session")
    private Set<RusPiece> rusPieces;

    @OneToMany(mappedBy = "session")
    private Set<RfPiece> rfPieces;

}
