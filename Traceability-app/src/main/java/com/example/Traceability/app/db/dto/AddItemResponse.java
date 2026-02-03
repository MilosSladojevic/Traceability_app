package com.example.Traceability.app.db.dto;

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
