package com.example.Traceability.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, reason = "Required request data is missing")
public class MyCostumeRequestMissingException extends RuntimeException{

    public MyCostumeRequestMissingException(String message) {
        super(message);
    }

}
