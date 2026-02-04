package com.example.Traceability.app.config;

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
