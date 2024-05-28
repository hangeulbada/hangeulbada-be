package com.hangeulbada.domain.workbookset.exception;

import com.hangeulbada.domain.workbookset.dto.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j(topic = "EXCEPTION_HANDLER")
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity(new ExceptionResponse(new Date(),ex.getStatus(), ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler(value = { NotAuthorizedException.class })
    public ResponseEntity<Object> handleNotAuthorizedException(NotAuthorizedException ex) {
        return new ResponseEntity(new ExceptionResponse(new Date(),ex.getStatus(), ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler(value = { WorkbookException.class })
    public ResponseEntity<Object> handleWorkbookException(WorkbookException ex) {
        return new ResponseEntity(new ExceptionResponse(new Date(),ex.getStatus(), ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler(value = { S3UploadException.class})
    public ResponseEntity<Object> handleS3UploadException(S3UploadException ex){
        return new ResponseEntity<>(new ExceptionResponse(new Date(),ex.getStatus(), ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler(value = { TtsException.class})
    public ResponseEntity<Object> handleTtsException(TtsException ex){
        return new ResponseEntity<>(new ExceptionResponse(new Date(),ex.getStatus(), ex.getMessage()), ex.getStatus());
    }
}
