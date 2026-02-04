package com.example.Traceability.app.db.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PieceDto {

    private String qrCode;
    private Long sessionId;

}
