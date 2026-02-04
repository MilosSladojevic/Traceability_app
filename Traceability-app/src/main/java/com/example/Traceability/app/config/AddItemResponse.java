package com.example.Traceability.app.config;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemResponse {
    private boolean saved;
    private String message;
}
