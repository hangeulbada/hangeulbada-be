package com.hangeulbada.domain.workbookset.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends RuntimeException{
    private String message;

    public NotAuthorizedException(String message) {
        super(message);
        this.message = message;
    }
}
