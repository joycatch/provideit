package com.provideit.assignment.handlers;

import com.provideit.assignment.error.CustomErrorResponse;
import com.provideit.assignment.error.InvalidRequestParameterCombinationException;
import com.provideit.assignment.error.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ ProductNotFoundException.class })
    public ResponseEntity<Object> handleProductNotFoundException(Exception e) {
        log.error("ProductNotFoundException: " + e.getMessage());
        return getResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ InvalidRequestParameterCombinationException.class })
    public ResponseEntity<Object> handleInvalidRequestParameterCombinationException(Exception e) {
        log.error("InvalidRequestParameterCombinationException: " + e.getMessage());
        return getResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponseEntity(Exception e, HttpStatus httpStatus) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(e.getMessage());
        errors.setStatus(httpStatus.value());
        return new ResponseEntity<>(errors, httpStatus);
    }
}