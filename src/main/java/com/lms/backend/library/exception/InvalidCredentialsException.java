package com.lms.backend.library.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
