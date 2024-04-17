package com.hangeulbada.domain.workbookset.exception;

import org.springframework.http.HttpStatus;

public class WorkbookException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public WorkbookException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public WorkbookException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
