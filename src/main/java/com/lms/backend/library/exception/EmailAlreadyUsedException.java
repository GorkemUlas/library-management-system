package com.lms.backend.library.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String message) { super(message); }
    public EmailAlreadyUsedException(String message, Throwable cause) { super(message, cause); }
}