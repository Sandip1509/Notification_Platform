package com.technologyzer.notificationapi.exception.handler;

import com.technologyzer.notificationapi.exception.AbstractException;
import com.technologyzer.notificationapi.exception.ResourceNotFoundException;
import com.technologyzer.notificationapi.exception.ValidationException;
import com.technologyzer.notificationapi.utils.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
                                                MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return genericExceptionHandler(e, () ->ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        return genericExceptionHandler(e, ()-> ResponseEntity.badRequest().body(e.getMessage()));
    }

    private ResponseEntity<String> genericExceptionHandler(final AbstractException e,
                                         final Supplier<ResponseEntity<String>> runner) {
        if(CommonUtils.isNotEmpty(e.getStatusCode())){
            return ResponseEntity.status(e.getStatusCode()).body(e.getErrorMessage());
        }
        return runner.get();
    }
}
