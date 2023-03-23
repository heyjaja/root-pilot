package com.root.pilot.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
public class TestExceptionResponse {

    private String service;
    private String serviceType;
    private LocalDateTime timestamp;
    private String exception;
    private String location;
    private String message;
    private String client;
    private String apiVersion;
    private String logLevel;
    private String stackTrace;

    public TestExceptionResponse(String message, String client, String location, String stackTrace) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.client = client;
        this.location = location;
        this.stackTrace = stackTrace;
    }

    @Builder
    public TestExceptionResponse(String service, String serviceType,
        String exception, String location, String message, String client, String apiVersion,
        String logLevel, String stackTrace) {
        this.service = service;
        this.serviceType = serviceType;
        this.timestamp = LocalDateTime.now();
        this.exception = exception;
        this.location = location;
        this.message = message;
        this.client = client;
        this.apiVersion = apiVersion;
        this.logLevel = logLevel;
        this.stackTrace = stackTrace;
    }
}
