package db;

import db.enums.ReasonForToolReplacement;
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
@Table(name="setup_pieces")
public class SetupPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    private ReasonForToolReplacement reason;

    @OneToMany(mappedBy = "setupPiece",cascade = CascadeType.ALL)
    private Measure badMeasures;

    private String comment;
}
