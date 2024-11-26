package com.hangeulbada.domain.workbookset.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class NoIncorrectsException extends RuntimeException{

    private String tagName;
    private String studentId;
    private String message;
    private HttpStatus status;

    public NoIncorrectsException(String tagName, String studentId) {
        super(String.format("user %s does not have incorrect questions tagged as '%s'", studentId, tagName));
        this.message = "해당 종류의 오답 기록이 없습니다.";
        this.status = HttpStatus.NOT_FOUND;
    }
}
