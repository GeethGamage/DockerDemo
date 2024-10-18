package com.stackstitch.docker.dto;

import lombok.Data;

@Data
public class ErrorDetails {

    private int status;
    private String message;
    private String details;

    public ErrorDetails(int status, String message, String details){
        this.status = status;
        this.message = message;
        this.details = details;

    }
}
