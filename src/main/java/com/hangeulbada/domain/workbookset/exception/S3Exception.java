package com.hangeulbada.domain.workbookset.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
public class S3Exception extends RuntimeException{
    private HttpStatus status;
    private String message;

    public S3Exception(String message){
        super(message);
        this.status=HttpStatus.INTERNAL_SERVER_ERROR;
        this.message=message;
    }
}
