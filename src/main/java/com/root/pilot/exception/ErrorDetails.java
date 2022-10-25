package com.root.pilot.exception;

import lombok.Getter;

@Getter
public class ErrorDetails {
    private String error;
    private String message;
    private String details;

    public ErrorDetails(String error, String message, String details) {
        this.error = error;
        this.details = details;
        this.message = message;
    }

}
