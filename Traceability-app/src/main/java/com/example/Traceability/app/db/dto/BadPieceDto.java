package com.example.Traceability.app.db.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadPieceDto {
    private Long sessionId;
    private String qrCode;
    private String comment;
    private String problem;
}
