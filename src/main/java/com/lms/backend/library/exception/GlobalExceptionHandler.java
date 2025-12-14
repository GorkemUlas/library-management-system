package com.lms.backend.library.exception;


import com.lms.backend.library.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<?> handleDuplicateIsbn(DuplicateIsbnException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(UserService.EmailAlreadyUsedException.class)
    public ResponseEntity<Map<String,String>> handleEmailConflict(
            UserService.EmailAlreadyUsedException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "error", "EMAIL_ALREADY_USED",
                        "message", ex.getMessage()
                ));}

    @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Map<String, String>> handleDataIntegrity (
                DataIntegrityViolationException ex){

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "DATA_INTEGRITY_VIOLATION",
                            "message", "Database constraint violation"
                    ));


        }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime (RuntimeException ex){
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleInvalidLogin(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "error", "INVALID_CREDENTIALS",
                        "message", ex.getMessage()
                ));
    }


}