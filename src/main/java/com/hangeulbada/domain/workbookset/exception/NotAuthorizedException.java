package com.hangeulbada.domain.workbookset.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@Getter
@Setter
@ResponseStatus(value= HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public NotAuthorizedException(String message) {
        this.message = message;
        this.status = HttpStatus.UNAUTHORIZED;
    }
}
