package com.example.Traceability.app.db.dto;

import com.example.Traceability.app.db.entity.enums.PieceType;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PieceDto {

    private String qrCode;
    private Long sessionId;

}
