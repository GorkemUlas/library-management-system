package com.lms.backend.library.exception;

public class DuplicateIsbnException extends RuntimeException {
    public DuplicateIsbnException(String isbn) {
        super("Duplicate isbn: " + isbn);
    }
}
