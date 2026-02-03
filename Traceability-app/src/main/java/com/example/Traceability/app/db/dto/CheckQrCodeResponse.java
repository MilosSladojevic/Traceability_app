package com.example.Traceability.app.db.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckQrCodeResponse {
    private boolean inDataBase;
    private String message;
}
