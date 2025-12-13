package com.lms.backend.library.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<ISBN, String> {

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null) return false;

        isbn = isbn.replace("-", "").replace(" ", "");

        if (isbn.length() == 10) {
            return isValidIsbn10(isbn);
        } else if (isbn.length() == 13) {
            return isValidIsbn13(isbn);
        }

        return false;
    }

    private boolean isValidIsbn10(String isbn) {
        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Integer.parseInt(isbn.substring(i, i + 1));
                sum += (digit * (10 - i));
            }

            char checksum = isbn.charAt(9);
            sum += (checksum == 'X') ? 10 : Integer.parseInt(String.valueOf(checksum));

            return sum % 11 == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidIsbn13(String isbn) {
        try {
            int sum = 0;

            for (int i = 0; i < 12; i++) {
                int digit = Integer.parseInt(isbn.substring(i, i + 1));
                sum += (i % 2 == 0) ? digit : digit * 3;
            }

            int checksum = 10 - (sum % 10);
            if (checksum == 10) checksum = 0;

            return checksum == Integer.parseInt(isbn.substring(12));
        } catch (Exception e) {
            return false;
        }
    }


}
