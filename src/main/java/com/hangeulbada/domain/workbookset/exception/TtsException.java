package com.hangeulbada.domain.workbookset.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value=HttpStatus.BAD_GATEWAY)
public class TtsException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public TtsException(String message){
        super(message);
        this.status=HttpStatus.BAD_GATEWAY;
        this.message=message;
    }
}
