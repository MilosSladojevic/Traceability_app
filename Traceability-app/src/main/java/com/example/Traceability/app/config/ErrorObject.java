package com.example.Traceability.app.config;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorObject {
    private String text;
    private String message;
}
