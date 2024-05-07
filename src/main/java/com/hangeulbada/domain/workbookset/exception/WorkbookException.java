package com.hangeulbada.domain.workbookset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class WorkbookException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public WorkbookException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
