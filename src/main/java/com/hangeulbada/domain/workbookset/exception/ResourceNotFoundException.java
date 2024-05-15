package com.hangeulbada.domain.workbookset.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName; //define this exception for a resource
    private String fieldName;
    private String fieldValue;
    private String message;
    private HttpStatus status;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.message = "존재하지 않는 객체입니다.";
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.status = HttpStatus.NOT_FOUND;
    }
}
