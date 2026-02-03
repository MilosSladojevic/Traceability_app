package com.example.Traceability.app.db.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionViewDto {

    private LocalDateTime startOfSession;
    private LocalDateTime endOfSession;
    private String noOutbox;
    private int okPiece;
    private int controlledPieces;
    private int setupPieces;
    private int rusPieces;
    private int rfPieces;
    private String employeeFullName;
}
